package be.digitalcity.projetspringrest.mappers;

import be.digitalcity.projetspringrest.models.dtos.ProductDto;
import be.digitalcity.projetspringrest.models.entities.Product;
import be.digitalcity.projetspringrest.models.forms.ProductForm;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    public ProductDto entityToDto(Product entity){
        ProductDto dto = new ProductDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCategory(entity.getCategory());
        return dto;
    }
    public Product formToEntity(ProductForm form){
        Product entity = new Product();
        entity.setName(form.getName());
        entity.setCategory(form.getCategory());
        return entity;
    }


}
