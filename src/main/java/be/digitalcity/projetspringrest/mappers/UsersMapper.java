package be.digitalcity.projetspringrest.mappers;


import be.digitalcity.projetspringrest.models.dtos.OmnithequeDto;
import be.digitalcity.projetspringrest.models.entities.Borrow;
import be.digitalcity.projetspringrest.models.entities.Omnitheque;
import be.digitalcity.projetspringrest.models.entities.Users;
import be.digitalcity.projetspringrest.models.forms.UsersForm;
import org.springframework.stereotype.Service;

@Service
public class UsersMapper {
    private final AddressMapper addressMapper;
    private final ProductMapper productMapper;

    public UsersMapper(AddressMapper adressMapper, ProductMapper productMapper) {
        this.addressMapper = adressMapper;
        this.productMapper = productMapper;
    }

    public Users formToEntity(UsersForm form){
        Users user = new Users();
        user.setId(form.getId());
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setBirthdate(form.getBirthdate());
        user.setAddress(addressMapper.formToEntity(form.getAddress()));
        user.setPhone(form.getPhone());
        user.setEmail(form.getEmail());
        return user;
    }
}
