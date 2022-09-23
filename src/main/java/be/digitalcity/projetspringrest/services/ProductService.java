package be.digitalcity.projetspringrest.services;

import be.digitalcity.projetspringrest.mappers.ProductMapper;
import be.digitalcity.projetspringrest.models.dtos.ProductDto;
import be.digitalcity.projetspringrest.models.entities.Product;
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

}
