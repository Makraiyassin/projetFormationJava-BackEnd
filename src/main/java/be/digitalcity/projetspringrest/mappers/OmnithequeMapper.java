package be.digitalcity.projetspringrest.mappers;


import be.digitalcity.projetspringrest.models.dtos.OmnithequeDto;
import be.digitalcity.projetspringrest.models.entities.Borrow;
import be.digitalcity.projetspringrest.models.entities.Omnitheque;
import org.springframework.stereotype.Service;

@Service
public class OmnithequeMapper {
    private final AddressMapper adressMapper;
    private final ProductMapper productMapper;

    public OmnithequeMapper(AddressMapper adressMapper, ProductMapper productMapper) {
        this.adressMapper = adressMapper;
        this.productMapper = productMapper;
    }

    public OmnithequeDto entityToDto(Omnitheque entity){
        OmnithequeDto dto = new OmnithequeDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        dto.setAddress(adressMapper.entityToDto(entity.getAddress()));
        dto.setOwnerId(entity.getOwner().getId());
        dto.setBorrowIdList(entity.getBorrowList().stream().map(Borrow::getId).toList());
        dto.setProductList(entity.getProductList().stream().map(productMapper::entityToDto).toList());
        return dto;
    }
}
