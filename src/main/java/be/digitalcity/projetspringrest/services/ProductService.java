package be.digitalcity.projetspringrest.services;

import be.digitalcity.projetspringrest.mappers.ProductMapper;
import be.digitalcity.projetspringrest.models.dtos.AddressDto;
import be.digitalcity.projetspringrest.models.dtos.ProductDto;
import be.digitalcity.projetspringrest.models.entities.Omnitheque;
import be.digitalcity.projetspringrest.models.entities.Product;
import be.digitalcity.projetspringrest.models.forms.AddressForm;
import be.digitalcity.projetspringrest.models.forms.ProductForm;
import be.digitalcity.projetspringrest.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ProductService {
    private final ProductMapper mapper;
    private final ProductRepository repository;

    public ProductService(ProductMapper mapper, ProductRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public ProductDto getOne(Long id){
        Product product = repository.findById(id).orElseThrow(()->new EntityNotFoundException("Aucun produit trouvé avec l'id {"+id+"}"));
        return mapper.entityToDto(product);
    }
    public List<ProductDto> getAll(){
        return repository.findAll().stream().map(mapper::entityToDto).toList();
    }
    public ProductDto create(ProductForm form){
        if( form == null) throw new IllegalArgumentException("le formulaire ne peut pas être null");
        return mapper.entityToDto(repository.save(mapper.formToEntity(form) ));
    }

    public ProductDto update(Long id,ProductForm form){
        if(form == null || id == null)
            throw new IllegalArgumentException("le formulaire et l'id ne peuvent pas être null");

        Product toUpdate = repository.findById(id).orElseThrow(()->new EntityNotFoundException("Aucun produit trouvé avec l'id {"+id+"}"));

        if(form.getName() != null) toUpdate.setName(form.getName());
        if(form.getCategory() != null) toUpdate.setCategory(form.getCategory());

        return mapper.entityToDto(repository.save(toUpdate));
    }

    public ProductDto delete(Long id){
        if(id == null)
            throw new IllegalArgumentException("l'id ne peut pas être null");

        Product product = repository.findById(id).orElseThrow(()->new EntityNotFoundException("Aucun produit trouvé avec l'id {"+id+"}"));
        repository.delete(product);
        return mapper.entityToDto(product);
    }


}
