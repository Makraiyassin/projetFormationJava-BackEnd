package be.digitalcity.projetspringrest.services;

import be.digitalcity.projetspringrest.mappers.AddressMapper;
import be.digitalcity.projetspringrest.models.dtos.AddressDto;
import be.digitalcity.projetspringrest.models.dtos.ProductDto;
import be.digitalcity.projetspringrest.models.entities.Address;
import be.digitalcity.projetspringrest.models.entities.Omnitheque;
import be.digitalcity.projetspringrest.models.entities.Product;
import be.digitalcity.projetspringrest.models.forms.AddressForm;
import be.digitalcity.projetspringrest.repositories.AddressRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    private final AddressMapper mapper;
    private final AddressRepository repository;

    public AddressService(AddressMapper mapper, AddressRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }


    public AddressDto getOne(Long id){
        Address address = repository.findById(id).orElseThrow(()->new EntityNotFoundException("Aucune addresse trouvé avec l'id {"+id+"}"));
        return mapper.entityToDto(address);
    }

    public List<AddressDto> getAll(){
        return repository.findAll().stream().map(mapper::entityToDto).toList();
    }

    public AddressDto create(AddressForm form){
        if( form == null) throw new IllegalArgumentException("le formulaire ne peut pas être null");

        Address addressChecked = search(form) ;
        if(addressChecked != null) return mapper.entityToDto(addressChecked);
        return mapper.entityToDto(repository.save(mapper.formToEntity(form) ));
    }

    public AddressDto update(Long id, AddressForm form){
        if(form == null || id == null)
            throw new IllegalArgumentException("le formulaire et l'id ne peuvent pas être null");

        Address toUpdate = repository.findById(id).orElseThrow(()->new EntityNotFoundException("Aucune addresse trouvé avec l'id {"+id+"}"));

        if(form.getStreet() != null) toUpdate.setStreet(form.getStreet());
        if(form.getNumber() != null) toUpdate.setNumber(form.getNumber());
        if(form.getCp() != null) toUpdate.setCp(form.getCp());
        if(form.getCity() != null) toUpdate.setCity(form.getCity());

        return mapper.entityToDto(repository.save(toUpdate));    }

    public AddressDto delete(Long id){
        if(id == null)
            throw new IllegalArgumentException("l'id ne peut pas être null");

        Address address = repository.findById(id).orElseThrow(()->new EntityNotFoundException("Aucune addresse trouvé avec l'id {"+id+"}"));
        repository.delete(address);
        return mapper.entityToDto(address);
    }

    protected Address search(AddressForm form){
        Optional<Address> addressChecked = repository.findDistinctByStreetAndNumberAndCp(form.getStreet(), form.getNumber(), form.getCp());
        return addressChecked.orElse(null);
    }

}
