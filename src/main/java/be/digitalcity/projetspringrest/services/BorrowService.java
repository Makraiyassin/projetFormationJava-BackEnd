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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

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
        Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("aucun produit ne correstpond à l'id {"+productId+"}"));

        //TODO: verifier que le produit exist dans l'omnitheque
        if(!omnitheque.getProductList().contains(product)) throw new EntityNotFoundException("le produit avec l'id{"+productId+"} ne se trouve pas dans l'omnitheque avec l'id{"+omnithequeId+"}");
        borrow.setOmnitheque(omnitheque);
        borrow.setProduct(product);

        Users user = usersRepository.findByEmail(auth.getName()).orElseThrow(() -> new EntityNotFoundException("aucun utilisateur trouver avec l'email {"+auth.getName()+"}"));

        //TODO: verifier que l'utilisateur a emprunter moins de 10 produits
        if(repository.countAllByUserAndEndBorrowAfter(user,LocalDate.now()) >= 10) throw new UnauthorizedException("vous avez atteint le nombre maximum d'emprunt");

        //TODO: verifier que le produit est dispo
        if(repository.countAllByProductAndEndBorrowAfterAndReturnedIsFalse(product,LocalDate.now())>= product.getQuantity()) throw  new UnavailableProductException(product.getName()+" est indisponible actuellement");

        //TODO: verifier que l'utilisateur n'a pas un emprunt en cours sur ce meme produits
        if(user.getBorrowList().stream().anyMatch(b -> {
            return(
                    b.getProduct().getId().equals(productId) &&
                    b.getEndBorrow().isAfter(LocalDate.now()) &&
                    !b.isReturned()
            );
        })) throw new UnauthorizedException("Vous avez un emprunt en cours contenant le produit avec l'id{"+productId+"}");


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


    public BorrowDto returnProduct(Authentication auth, @RequestParam Long borrowId){
        if( borrowId == null ) throw new IllegalArgumentException("l'id de l'emprunt ne peut pas être null");

        Borrow borrow = repository.findById(borrowId).orElseThrow(()-> new EntityNotFoundException("le emprunt avec l'id{"+borrowId+"} n'a été trouver"));

        Users user = usersRepository.findByEmail(auth.getName()).orElseThrow(
                ()->new UsernameNotFoundException("Une erreur est survenu lors de la connexion. veuillez verifier votre identifiant")
        );
        Omnitheque omnitheque;

        if(user.getOmnitheque() != null) omnitheque = user.getOmnitheque();
        else throw new EntityNotFoundException("aucune omnithque n'a été trouver chez l'utilisateur avec l'id {"+user.getId()+"}");

        if(omnitheque.getBorrowList().stream().noneMatch(b-> b.getId().equals(borrowId)))
            throw new UnauthorizedException("l'emprunt avec l'id {"+borrowId+"} n'appartient pas à l'omnithèque avec l'id{"+omnitheque.getId()+"}");

        if(borrow.isReturned()) return mapper.entityToDto(borrow);
        borrow.setReturned(true);
        return mapper.entityToDto(repository.save(borrow));

    }
}
