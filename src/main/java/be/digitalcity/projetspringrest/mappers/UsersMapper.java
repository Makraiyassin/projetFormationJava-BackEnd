package be.digitalcity.projetspringrest.mappers;


import be.digitalcity.projetspringrest.models.dtos.UsersDto;
import be.digitalcity.projetspringrest.models.entities.Users;
import be.digitalcity.projetspringrest.models.forms.UsersForm;
import org.springframework.stereotype.Service;

@Service
public class UsersMapper {
    private final AddressMapper addressMapper;
    private final OmnithequeMapper omnithequeMapper;

    public UsersMapper(AddressMapper addressMapper, OmnithequeMapper omnithequeMapper) {
        this.addressMapper = addressMapper;
        this.omnithequeMapper = omnithequeMapper;
    }


    public Users formToEntity(UsersForm form){
        Users user = new Users();
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setBirthdate(form.getBirthdate());
        user.setPhone(form.getPhone());
        user.setEmail(form.getEmail());
        return user;
    }
    public UsersDto entityToDto(Users entity){
        UsersDto dto = new UsersDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setBirthdate(entity.getBirthdate());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        dto.setAddress(addressMapper.entityToDto(entity.getAddress()));
        if(entity.getOmnitheque() != null)
            dto.setOmnitheque(omnithequeMapper.entityToDto(entity.getOmnitheque()));

        //TODO borrow mapper (regler import recursive !!! )

        return dto;
    }
}
