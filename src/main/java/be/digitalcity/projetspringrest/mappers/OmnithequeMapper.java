package be.digitalcity.projetspringrest.mappers;


import be.digitalcity.projetspringrest.models.dtos.OmnithequeDto;
import be.digitalcity.projetspringrest.models.entities.Omnitheque;
import be.digitalcity.projetspringrest.models.forms.OmnithequeForm;
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
        if(entity.getBorrowList() != null)
            dto.setBorrowList(entity.getBorrowList().stream().map(borrowMapper::entityToDto).toList());
        if(entity.getProductList() != null)
            dto.setProductList(entity.getProductList().stream().map(productMapper::entityToDto).toList());
        return dto;
    }

    public Omnitheque formToEntity(OmnithequeForm form){
        Omnitheque entity = new Omnitheque();
        entity.setName(form.getName());
        entity.setEmail(form.getEmail());
        entity.setPhone(form.getPhone());
        return entity;
    }
}
