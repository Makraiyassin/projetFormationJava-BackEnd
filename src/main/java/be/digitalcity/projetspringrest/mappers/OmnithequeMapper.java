package be.digitalcity.projetspringrest.mappers;


import be.digitalcity.projetspringrest.models.dtos.OmnithequeDto;
import be.digitalcity.projetspringrest.models.entities.Borrow;
import be.digitalcity.projetspringrest.models.entities.Omnitheque;
import org.springframework.stereotype.Service;

@Service
public class OmnithequeMapper {
    private final AddressMapper adressMapper;
    private final ProductMapper productMapper;
    private final BorrowMapper borrowMapper;

    public OmnithequeMapper(AddressMapper adressMapper, ProductMapper productMapper, BorrowMapper borrowMapper) {
        this.adressMapper = adressMapper;
        this.productMapper = productMapper;
        this.borrowMapper = borrowMapper;
    }

    public OmnithequeDto entityToDto(Omnitheque entity){
        OmnithequeDto dto = new OmnithequeDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        dto.setAddress(adressMapper.entityToDto(entity.getAddress()));
        dto.setBorrowList(entity.getBorrowList().stream().map(borrowMapper::entityToDto).toList());
        dto.setProductList(entity.getProductList().stream().map(productMapper::entityToDto).toList());
        return dto;
    }
}
