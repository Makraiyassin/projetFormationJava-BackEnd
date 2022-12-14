package be.digitalcity.projetspringrest.mappers;


import be.digitalcity.projetspringrest.models.dtos.OmnithequeDto;
import be.digitalcity.projetspringrest.models.entities.Omnitheque;
import be.digitalcity.projetspringrest.models.forms.OmnithequeForm;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class OmnithequeMapper {
    private final AddressMapper adressMapper;
    private final ProductMapper productMapper;
    private final BorrowMapper borrowMapper;
    private final PostMapper postMapper;



    public OmnithequeMapper(AddressMapper adressMapper, ProductMapper productMapper, BorrowMapper borrowMapper, PostMapper postMapper) {
        this.adressMapper = adressMapper;
        this.productMapper = productMapper;
        this.borrowMapper = borrowMapper;
        this.postMapper = postMapper;
    }

    public OmnithequeDto entityToDto(Omnitheque entity){
        OmnithequeDto dto = new OmnithequeDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        dto.setAddress(adressMapper.entityToDto(entity.getAddress()));
        dto.setImage(entity.getImage());
        if(entity.getBorrowList() != null)
            dto.setBorrowList(entity.getBorrowList().stream().map(borrowMapper::entityToDto).collect(Collectors.toList()));
        if(entity.getProductList() != null)
            dto.setProductList(entity.getProductList().stream().map(productMapper::entityToDto).collect(Collectors.toList()));
        if(entity.getPostList() != null)
            dto.setPostList(entity.getPostList().stream().map(postMapper::entityToDto).collect(Collectors.toList()));
        return dto;
    }

    public Omnitheque formToEntity(OmnithequeForm form){
        Omnitheque entity = new Omnitheque();
        entity.setName(form.getName());
        entity.setEmail(form.getEmail());
        entity.setPhone(form.getPhone());
        entity.setImage(form.getImage());
        return entity;
    }
}
