package be.digitalcity.projetspringrest.services;

import be.digitalcity.projetspringrest.mappers.AddressMapper;
import be.digitalcity.projetspringrest.mappers.OmnithequeMapper;
import be.digitalcity.projetspringrest.models.dtos.OmnithequeDto;
import be.digitalcity.projetspringrest.models.entities.Address;
import be.digitalcity.projetspringrest.models.entities.Omnitheque;
import be.digitalcity.projetspringrest.models.forms.OmnithequeForm;
import be.digitalcity.projetspringrest.repositories.AddressRepository;
import be.digitalcity.projetspringrest.repositories.OmnithequeRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class OmnithequeService {
    private final OmnithequeMapper mapper;
    private final OmnithequeRepository repository;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final AddressService addressService;
    private final UsersDetailsServiceImpl usersService;

    public OmnithequeService(OmnithequeMapper mapper, OmnithequeRepository repository, AddressRepository addressRepository, AddressMapper addressMapper, AddressService addressService, UsersDetailsServiceImpl usersService) {
        this.mapper = mapper;
        this.repository = repository;
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
        this.addressService = addressService;
        this.usersService = usersService;
    }

    public OmnithequeDto getOne(Long id){
        Omnitheque omnitheque = repository.findById(id).orElseThrow(()->new EntityNotFoundException("Aucune omnitheque trouvé avec l'id {"+id+"}"));
        return mapper.entityToDto(omnitheque);
    }
    public List<OmnithequeDto> getAll(){
        return repository.findAll().stream().map(mapper::entityToDto).toList();
    }

    public OmnithequeDto create(OmnithequeForm form, Authentication auth){
        if( form == null) throw new IllegalArgumentException("le formulaire ne peut pas être null");
        Omnitheque omnitheque = mapper.formToEntity( form );
        Address addressChecked = addressService.search(form.getAddress());
        if(addressChecked != null ) {
            omnitheque.setAddress(addressChecked);
        }else {
            omnitheque.setAddress(addressRepository.save(addressMapper.formToEntity(form.getAddress())));
        }

        return mapper.entityToDto(usersService.addOmnitheque(auth,omnitheque));

    }
    public OmnithequeDto update( Authentication auth, OmnithequeForm form){
        if( form == null) throw new IllegalArgumentException("le formulaire ne peut pas être null");

        Omnitheque toUpdate = repository.findById(form.getId()).orElseThrow(()->new EntityNotFoundException("Aucune omnitheque trouvé avec l'id {"+form.getId()+"}"));

        if(form.getName() != null) toUpdate.setName(form.getName());
        if(form.getEmail() != null) toUpdate.setEmail(form.getEmail());
        if(form.getPhone() != null) toUpdate.setPhone(form.getPhone());

        if(form.getAddress() != null) {
            Address addressChecked = addressService.search(form.getAddress());
            if(addressChecked != null ) {
                toUpdate.setAddress(addressChecked);
            }else {
                toUpdate.setAddress(addressRepository.save(addressMapper.formToEntity(form.getAddress())));
            }
        }
        return mapper.entityToDto(repository.save(toUpdate));
    }

    public OmnithequeDto delete(Long id){
        if(id == null)
            throw new IllegalArgumentException("l'id ne peut pas être null");

        Omnitheque omnitheque = repository.findById(id).orElseThrow(()->new EntityNotFoundException("Aucune omnitheque trouvé avec l'id {"+id+"}"));
        repository.delete(omnitheque);
        return mapper.entityToDto(omnitheque);
    }
}
