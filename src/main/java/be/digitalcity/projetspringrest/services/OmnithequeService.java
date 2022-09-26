package be.digitalcity.projetspringrest.services;

import be.digitalcity.projetspringrest.mappers.AddressMapper;
import be.digitalcity.projetspringrest.mappers.OmnithequeMapper;
import be.digitalcity.projetspringrest.models.dtos.OmnithequeDto;
import be.digitalcity.projetspringrest.models.entities.Omnitheque;
import be.digitalcity.projetspringrest.models.forms.OmnithequeForm;
import be.digitalcity.projetspringrest.repositories.AddressRepository;
import be.digitalcity.projetspringrest.repositories.OmnithequeRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class OmnithequeService {
    private final OmnithequeMapper mapper;
    private final OmnithequeRepository repository;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public OmnithequeService(OmnithequeMapper mapper, OmnithequeRepository repository, AddressRepository addressRepository, AddressMapper addressMapper) {
        this.mapper = mapper;
        this.repository = repository;
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    public OmnithequeDto getOne(Long id){
        Omnitheque omnitheque = repository.findById(id).orElseThrow(()->new EntityNotFoundException("Aucune omnitheque trouvé avec l'id {"+id+"}"));
        return mapper.entityToDto(omnitheque);
    }
    public List<OmnithequeDto> getAll(){
        return repository.findAll().stream().map(mapper::entityToDto).toList();
    }

    public OmnithequeDto create(OmnithequeForm form){
        if( form == null) throw new IllegalArgumentException("le formulaire ne peut pas être null");

        Omnitheque omnitheque = mapper.formToEntity( form );
        omnitheque.setAddress(addressRepository.save(addressMapper.formToEntity(form.getAddress())));

        return mapper.entityToDto(repository.save( omnitheque ));

    }

    public OmnithequeDto update(Long id, OmnithequeForm form){
        if(form == null || id == null)
            throw new IllegalArgumentException("le formulaire et l'id ne peuvent pas être null");

        Omnitheque toUpdate = repository.findById(id).orElseThrow(()->new EntityNotFoundException("Aucune omnitheque trouvé avec l'id {"+id+"}"));

        if(form.getName() != null) toUpdate.setName(form.getName());
        if(form.getEmail() != null) toUpdate.setEmail(form.getEmail());
        if(form.getAddress() != null) toUpdate.setAddress(addressRepository.save(addressMapper.formToEntity(form.getAddress())));
        if(form.getPhone() != null) toUpdate.setPhone(form.getPhone());

        return mapper.entityToDto(repository.save(toUpdate));
    }

    public OmnithequeDto delete(long id){
        return new OmnithequeDto();
    }

}
