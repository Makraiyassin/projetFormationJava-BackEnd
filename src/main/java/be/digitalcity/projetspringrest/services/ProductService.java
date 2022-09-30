package be.digitalcity.projetspringrest.services;

import be.digitalcity.projetspringrest.mappers.ProductMapper;
import be.digitalcity.projetspringrest.models.dtos.ProductDto;
import be.digitalcity.projetspringrest.models.entities.Omnitheque;
import be.digitalcity.projetspringrest.models.entities.Product;
import be.digitalcity.projetspringrest.models.forms.ProductForm;
import be.digitalcity.projetspringrest.repositories.OmnithequeRepository;
import be.digitalcity.projetspringrest.repositories.ProductRepository;
import be.digitalcity.projetspringrest.repositories.UsersRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ProductService {
    private final ProductMapper mapper;
    private final ProductRepository repository;
    private final OmnithequeService omnithequeService;
    private final UsersRepository usersRepository;
    private final OmnithequeRepository omnithequeRepository;

    public ProductService(ProductMapper mapper, ProductRepository repository, OmnithequeService omnithequeService, UsersRepository usersRepository, OmnithequeRepository omnithequeRepository) {
        this.mapper = mapper;
        this.repository = repository;

        this.omnithequeService = omnithequeService;
        this.usersRepository = usersRepository;
        this.omnithequeRepository = omnithequeRepository;
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
        Product product = mapper.formToEntity( form );
        Long omnithequeId = usersRepository.findByEmail(auth.getName()).get().getOmnitheque().getId();
        ProductDto productDto = mapper.entityToDto(omnithequeService.addProduct(auth,omnithequeId, product));
        productDto.setOmnithequeId((omnithequeId));
        return productDto;
    }

    public ProductDto update(Authentication auth, ProductForm form){
        if(form == null )
            throw new IllegalArgumentException("le formulaire et l'id ne peuvent pas être null");

        Product toUpdate = repository.findById(form.getId()).orElseThrow(()->new EntityNotFoundException("Aucun produit trouvé avec l'id {"+form.getId()+"}"));
        Omnitheque omnitheque = usersRepository.findByEmail(auth.getName()).get().getOmnitheque();
        if(omnitheque.getProductList().stream().anyMatch(p->p.getId().equals(form.getId())))
            if(form.getName() != null) toUpdate.setName(form.getName());
            if(form.getCategory() != null) toUpdate.setCategory(form.getCategory());
            if(form.getDescription() != null) toUpdate.setDescription(form.getDescription());
            if(form.getImage() != null) toUpdate.setImage(form.getImage());
            if(form.getQuantity() != null) toUpdate.setQuantity(form.getQuantity());
            toUpdate = repository.save(toUpdate);

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
    }
}