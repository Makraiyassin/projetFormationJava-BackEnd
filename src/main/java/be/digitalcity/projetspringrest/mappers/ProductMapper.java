package be.digitalcity.projetspringrest.mappers;

import be.digitalcity.projetspringrest.models.dtos.ProductDto;
import be.digitalcity.projetspringrest.models.entities.Product;
import be.digitalcity.projetspringrest.models.forms.ProductForm;
import be.digitalcity.projetspringrest.repositories.OmnithequeRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ProductMapper {
    private final OmnithequeRepository omnithequeRepository;
    private final BorrowMapper borrowMapper;

    public ProductMapper(OmnithequeRepository omnithequeRepository, BorrowMapper borrowMapper) {
        this.omnithequeRepository = omnithequeRepository;
        this.borrowMapper = borrowMapper;
    }

    public ProductDto entityToDto(Product entity){
        ProductDto dto = new ProductDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCategory(entity.getCategory());
        dto.setQuantity(entity.getQuantity());
        dto.setImage(entity.getImage());
        dto.setDescription(entity.getDescription());
        if(entity.getBorrowList() != null) dto.setBorrowList(entity.getBorrowList().stream().map(borrowMapper::entityToDto).collect(Collectors.toList()));
        if(entity.getOmnitheque() != null) dto.setOmnithequeId(entity.getOmnitheque().getId());

        return dto;
    }
    public Product formToEntity(ProductForm form){
        Product entity = new Product();
        entity.setName(form.getName());
        entity.setCategory(form.getCategory());
        entity.setImage(form.getImage());
        entity.setQuantity(form.getQuantity());
        entity.setDescription(form.getDescription());
        return entity;
    }


}
