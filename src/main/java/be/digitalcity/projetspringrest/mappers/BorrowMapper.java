package be.digitalcity.projetspringrest.mappers;

import be.digitalcity.projetspringrest.models.dtos.BorrowDto;
import be.digitalcity.projetspringrest.models.entities.Borrow;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;

@Service
public class BorrowMapper {
    private final ProductMapper productMapper;

    public BorrowMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }


    public BorrowDto entityToDto(Borrow entity){
        BorrowDto dto = new BorrowDto();
        dto.setId(entity.getId());
        dto.setStartBorrow(entity.getStartBorrow());
        dto.setEndBorrow(entity.getEndBorrow());
        dto.setUserId(entity.getUser().getId());
        dto.setOmnithequeId(entity.getOmnitheque().getId());
        dto.setProductId(entity.getProduct().getId());
        return dto;
    }
}
