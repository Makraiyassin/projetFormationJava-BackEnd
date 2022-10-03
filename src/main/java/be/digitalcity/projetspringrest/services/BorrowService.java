package be.digitalcity.projetspringrest.services;

import be.digitalcity.projetspringrest.exceptions.UnauthorizedException;
import be.digitalcity.projetspringrest.exceptions.UnavailableProductException;
import be.digitalcity.projetspringrest.mappers.BorrowMapper;
import be.digitalcity.projetspringrest.models.dtos.BorrowDto;
import be.digitalcity.projetspringrest.models.entities.Borrow;
import be.digitalcity.projetspringrest.models.entities.Omnitheque;
import be.digitalcity.projetspringrest.models.entities.Product;
import be.digitalcity.projetspringrest.models.entities.Users;
import be.digitalcity.projetspringrest.repositories.BorrowRepository;
import be.digitalcity.projetspringrest.repositories.OmnithequeRepository;
import be.digitalcity.projetspringrest.repositories.ProductRepository;
import be.digitalcity.projetspringrest.repositories.UsersRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;

@Service
public class BorrowService {

    private final BorrowRepository repository;
    private final OmnithequeRepository omnithequeRepository;
    private final ProductRepository productRepository;
    private final UsersRepository usersRepository;
    private final BorrowMapper mapper;

    public BorrowService(BorrowRepository repository, OmnithequeRepository omnithequeRepository, ProductRepository productRepository, UsersRepository usersRepository, BorrowMapper mapper) {
        this.repository = repository;
        this.omnithequeRepository = omnithequeRepository;
        this.productRepository = productRepository;
        this.usersRepository = usersRepository;
        this.mapper = mapper;
    }

    public BorrowDto create(Authentication auth, Long omnithequeId, Long productId){
        if( omnithequeId == null || productId == null ) throw new IllegalArgumentException("l'id de l'omnitheque  et du produit ne peuvent pas être null");

        Borrow borrow = new Borrow();
        borrow.setStartBorrow(LocalDate.now());
        borrow.setEndBorrow(LocalDate.now().plusDays(15));

        Omnitheque omnitheque = omnithequeRepository.findById(omnithequeId).orElseThrow(() -> new EntityNotFoundException("aucune Omnitheque ne correstpond à l'id {"+omnithequeId+"}"));
        borrow.setOmnitheque(omnitheque);

        Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("aucun produit ne correstpond à l'id {"+productId+"}"));
        borrow.getProductList().add(product);

        Users user = usersRepository.findByEmail(auth.getName()).orElseThrow(() -> new EntityNotFoundException("aucun utilisateur trouver avec l'email {"+auth.getName()+"}"));

        //TODO: verifier que l'utilisateur a emprunter moins de 10 produits
        if(repository.countAllByUserAndEndBorrowAfter(user,LocalDate.now()) >= 10) throw new UnauthorizedException("vous avez atteint le nombre maximum d'emprunt");

        //TODO: verifier que le produit est dispo
        if(repository.countAllByProductListContainsAndEndBorrowAfter(product,LocalDate.now())>= product.getQuantity()) throw  new UnavailableProductException(product.getName()+" est indisponible actuellement");

        //TODO: verifier que l'utilisateur n'a pas un emprunt en cours sur ce meme produits
        if(user.getBorrowList().stream().anyMatch(b -> {
            return(
                    b.getProductList().stream().anyMatch(p-> p.getId().equals(productId)) &&
                            b.getEndBorrow().isAfter(LocalDate.now())
            );
        })) throw new UnauthorizedException("Vous avez un emprunt en cours contenant ce produit");



        borrow.setUser(user);
        borrow =repository.save(borrow);

        omnitheque.getBorrowList().add(borrow);
        omnithequeRepository.save(omnitheque);
        product.getBorrowList().add(borrow);
        productRepository.save(product);
        user.getBorrowList().add(borrow);
        usersRepository.save(user);

        return mapper.entityToDto(borrow);
    }
}
