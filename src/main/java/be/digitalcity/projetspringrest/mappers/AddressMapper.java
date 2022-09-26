package be.digitalcity.projetspringrest.mappers;

import be.digitalcity.projetspringrest.models.dtos.AddressDto;
import be.digitalcity.projetspringrest.models.entities.Address;
import be.digitalcity.projetspringrest.models.forms.AddressForm;
import org.springframework.stereotype.Service;

@Service
public class AddressMapper {
    public AddressDto entityToDto(Address entity){
        AddressDto dto = new AddressDto();
        dto.setId(entity.getId());
        dto.setStreet(entity.getStreet());
        dto.setNumber(entity.getNumber());
        dto.setCp(entity.getCp());
        dto.setCity(entity.getCity());
        dto.setCountry(entity.getCountry());
        return dto;
    }

    public Address formToEntity(AddressForm form){
        Address entity = new Address();
        entity.setStreet(form.getStreet());
        entity.setNumber(form.getNumber());
        entity.setCp(form.getCp());
        entity.setCity(form.getCity());
        entity.setCountry(form.getCountry());
        return entity;
    }

}
