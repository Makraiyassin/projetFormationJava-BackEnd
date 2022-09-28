package be.digitalcity.projetspringrest.services;

import be.digitalcity.projetspringrest.mappers.ProductMapper;
import be.digitalcity.projetspringrest.models.dtos.ProductDto;
import be.digitalcity.projetspringrest.models.entities.Product;
import be.digitalcity.projetspringrest.models.forms.ProductForm;
import be.digitalcity.projetspringrest.repositories.ProductRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ProductService {
    private final ProductMapper mapper;
    private final ProductRepository repository;
    private final OmnithequeService omnithequeService;

    public ProductService(ProductMapper mapper, ProductRepository repository, OmnithequeService omnithequeService) {
        this.mapper = mapper;
        this.repository = repository;

        this.omnithequeService = omnithequeService;
    }

    public ProductDto getOne(Long id){
        Product product = repository.findById(id).orElseThrow(()->new EntityNotFoundException("Aucun produit trouvé avec l'id {"+id+"}"));
        return mapper.entityToDto(product);
    }

    public List<ProductDto> getAll(){
        return repository.findAll().stream().map(mapper::entityToDto).toList();
    }

    public ProductDto create(Authentication auth,  Long omnithequeId, ProductForm form){
        if( omnithequeId == null || form == null ) throw new IllegalArgumentException("le formulaire et l'id de l'omnitheque ne peuvent pas être null");
        Product product = mapper.formToEntity( form );
        return mapper.entityToDto(omnithequeService.addProduct(auth,omnithequeId, product));
    }

    public ProductDto update(Long id,ProductForm form, Authentication auth){
        if(form == null || id == null)
            throw new IllegalArgumentException("le formulaire et l'id ne peuvent pas être null");

        Product toUpdate = repository.findById(id).orElseThrow(()->new EntityNotFoundException("Aucun produit trouvé avec l'id {"+id+"}"));

        if(form.getName() != null) toUpdate.setName(form.getName());
        if(form.getCategory() != null) toUpdate.setCategory(form.getCategory());

        return mapper.entityToDto(repository.save(toUpdate));
    }

    public ProductDto delete(Long id, Authentication auth){
        if(id == null)
            throw new IllegalArgumentException("l'id ne peut pas être null");

        Product product = repository.findById(id).orElseThrow(()->new EntityNotFoundException("Aucun produit trouvé avec l'id {"+id+"}"));
        repository.delete(product);
        return mapper.entityToDto(product);
    }
}
