package be.digitalcity.projetspringrest.services;

import be.digitalcity.projetspringrest.mappers.AddressMapper;
import be.digitalcity.projetspringrest.mappers.UsersMapper;
import be.digitalcity.projetspringrest.models.dtos.UsersDto;
import be.digitalcity.projetspringrest.models.entities.Users;
import be.digitalcity.projetspringrest.models.forms.UsersForm;
import be.digitalcity.projetspringrest.repositories.AddressRepository;
import be.digitalcity.projetspringrest.repositories.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersDetailsServiceImpl implements UserDetailsService {

    private final UsersMapper mapper;
    private final AddressMapper addressMapper;
    private final UsersRepository repository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder encoder;

    public UsersDetailsServiceImpl(UsersMapper mapper, AddressMapper addressMapper, UsersRepository repository, AddressRepository addressRepository, PasswordEncoder encoder) {
        this.mapper = mapper;
        this.addressMapper = addressMapper;
        this.repository = repository;
        this.addressRepository = addressRepository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("connexion impossible"));
    }

    public UsersDto create(UsersForm form){
        Users user = mapper.formToEntity(form); //mapper en entity
        user.setAddress(addressRepository.save(addressMapper.formToEntity(form.getAddress())));
        user.setPassword( encoder.encode(form.getPassword()) );
        return mapper.entityToDto(repository.save( user ));
    }
}
