package be.digitalcity.projetspringrest.mappers;

import be.digitalcity.projetspringrest.models.dtos.AddressDto;
import be.digitalcity.projetspringrest.models.entities.Address;
import org.springframework.stereotype.Service;

@Service
public class AdressMapper {
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


}
