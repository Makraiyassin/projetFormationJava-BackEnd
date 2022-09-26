package be.digitalcity.projetspringrest.services;

import be.digitalcity.projetspringrest.mappers.AddressMapper;
import be.digitalcity.projetspringrest.mappers.UsersMapper;
import be.digitalcity.projetspringrest.models.dtos.UsersDto;
import be.digitalcity.projetspringrest.models.entities.Address;
import be.digitalcity.projetspringrest.models.entities.Users;
import be.digitalcity.projetspringrest.models.forms.AddressForm;
import be.digitalcity.projetspringrest.models.forms.UsersForm;
import be.digitalcity.projetspringrest.repositories.AddressRepository;
import be.digitalcity.projetspringrest.repositories.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;

@Service
public class UsersDetailsServiceImpl implements UserDetailsService {

    private final UsersMapper mapper;
    private final AddressMapper addressMapper;
    private final UsersRepository repository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder encoder;
    private final AddressService addressService;

    public UsersDetailsServiceImpl(UsersMapper mapper, AddressMapper addressMapper, UsersRepository repository, AddressRepository addressRepository, PasswordEncoder encoder, AddressService addressService) {
        this.mapper = mapper;
        this.addressMapper = addressMapper;
        this.repository = repository;
        this.addressRepository = addressRepository;
        this.encoder = encoder;
        this.addressService = addressService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("connexion impossible"));
    }

    public UsersDto create(UsersForm form){
        if(repository.findByEmail(form.getEmail()).isPresent())
            throw new EntityExistsException("l'address mail {"+form.getEmail()+"} est déjà lié à un utilisateur");

        Users user = mapper.formToEntity(form);
        Address addressChecked = addressService.search(form.getAddress());

        if(addressChecked != null ) {
            user.setAddress(addressChecked);
        }else {
            user.setAddress(addressRepository.save(addressMapper.formToEntity(form.getAddress())));
        }

        user.setPassword( encoder.encode(form.getPassword()) );
        return mapper.entityToDto(repository.save( user ));
    }

}
