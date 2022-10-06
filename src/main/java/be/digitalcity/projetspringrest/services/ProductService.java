package be.digitalcity.projetspringrest.services;

import be.digitalcity.projetspringrest.exceptions.UnauthorizedException;
import be.digitalcity.projetspringrest.mappers.ProductMapper;
import be.digitalcity.projetspringrest.models.dtos.ProductDto;
import be.digitalcity.projetspringrest.models.entities.Omnitheque;
import be.digitalcity.projetspringrest.models.entities.Product;
import be.digitalcity.projetspringrest.models.entities.Users;
import be.digitalcity.projetspringrest.models.forms.ProductForm;
import be.digitalcity.projetspringrest.repositories.ProductRepository;
import be.digitalcity.projetspringrest.repositories.UsersRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ProductService {
    private final ProductMapper mapper;
    private final ProductRepository repository;
    private final UsersRepository usersRepository;

    public ProductService(ProductMapper mapper, ProductRepository repository, UsersRepository usersRepository) {
        this.mapper = mapper;
        this.repository = repository;
        this.usersRepository = usersRepository;
    }

    public ProductDto getOne(Long id){
        Product product = repository.findById(id).orElseThrow(()->new EntityNotFoundException("Aucun produit trouvé avec l'id {"+id+"}"));
        return mapper.entityToDto(product);
    }

    public List<ProductDto> getAll(){
        return repository.findAll().stream().map(mapper::entityToDto).toList();
    }

    public ProductDto create(Authentication auth, ProductForm form){
        if( form == null ) throw new IllegalArgumentException("le formulaire et l'id de l'omnitheque ne peuvent pas être null");
        Long omnithequeId = usersRepository.findByEmail(auth.getName()).orElseThrow(()->
            new EntityNotFoundException("utilisateur"+auth.getName()+" n'existe pas")
        ).getOmnitheque().getId();

        form.setOmnithequeId(omnithequeId);
        Product product = mapper.formToEntity( form );
        return  mapper.entityToDto(repository.save(product));
    }

    public ProductDto update(Authentication auth, ProductForm form){
        if(form == null )
            throw new IllegalArgumentException("le formulaire et l'id ne peuvent pas être null");

        Product toUpdate = repository.findById(form.getId()).orElseThrow(()->new EntityNotFoundException("Aucun produit trouvé avec l'id {"+form.getId()+"}"));

        Users user;
        Omnitheque omnitheque;
        if(usersRepository.findByEmail(auth.getName()).isPresent()){
            user = usersRepository.findByEmail(auth.getName()).get();
            if(user.getOmnitheque() != null) omnitheque = user.getOmnitheque();
            else throw new EntityNotFoundException("l'utilisateur avec l'id {"+user.getId()+"} ne possede pas d'omnitheque");

        } else  throw new UsernameNotFoundException("Une erreur est survenu lors de la connexion. veuillez verifier votre identifiant");

        if(omnitheque.getProductList().stream().anyMatch(p->p.getId().equals(form.getId()))){
            if(form.getName() != null) toUpdate.setName(form.getName());
            if(form.getCategory() != null) toUpdate.setCategory(form.getCategory());
            if(form.getDescription() != null) toUpdate.setDescription(form.getDescription());
            if(form.getImage() != null) toUpdate.setImage(form.getImage());
            if(form.getQuantity() != null) toUpdate.setQuantity(form.getQuantity());
            toUpdate = repository.save(toUpdate);
        } else throw new UnauthorizedException("le produit avec l'id {"+form.getId()+"} n'appartient pas à  l'omnitheque avec l'id {"+omnitheque.getId()+"}");

        ProductDto productDto = mapper.entityToDto(toUpdate);
        productDto.setOmnithequeId((omnitheque.getId()));
        return productDto;
    }

    public ProductDto delete(Long id, Authentication auth){
        if(id == null)
            throw new IllegalArgumentException("l'id ne peut pas être null");


        ProductForm form = new ProductForm();
        form.setId(id);
        form.setQuantity(0);
        return update(auth, form);

////TODO: Verifier que le produit appartient bien à l'omnitheque de l'utilisateur
//        Product product = repository.findById(id).orElseThrow(()->new EntityNotFoundException("Aucun produit trouvé avec l'id {"+id+"}"));
//        repository.delete(product);
//        product.setId(null);
//        return mapper.entityToDto(product);
    }

    public List<ProductDto> search(String name) {
        return repository.findAllByNameContaining(name).stream().map(mapper::entityToDto).toList();
    }
}
