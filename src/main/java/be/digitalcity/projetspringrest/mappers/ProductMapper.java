package be.digitalcity.projetspringrest.mappers;

import be.digitalcity.projetspringrest.models.dtos.ProductDto;
import be.digitalcity.projetspringrest.models.entities.Product;
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


}
