package be.digitalcity.projetspringrest.services;

import be.digitalcity.projetspringrest.exceptions.ConnectionErrorException;
import be.digitalcity.projetspringrest.mappers.AddressMapper;
import be.digitalcity.projetspringrest.mappers.UsersMapper;
import be.digitalcity.projetspringrest.models.dtos.UsersDto;
import be.digitalcity.projetspringrest.models.entities.Address;
import be.digitalcity.projetspringrest.models.entities.Omnitheque;
import be.digitalcity.projetspringrest.models.entities.Users;
import be.digitalcity.projetspringrest.models.forms.UsersForm;
import be.digitalcity.projetspringrest.repositories.AddressRepository;
import be.digitalcity.projetspringrest.repositories.OmnithequeRepository;
import be.digitalcity.projetspringrest.repositories.RolesRepository;
import be.digitalcity.projetspringrest.repositories.UsersRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;

@Service
public class UsersService implements UserDetailsService {

    private final UsersMapper mapper;
    private final AddressMapper addressMapper;
    private final UsersRepository repository;
    private final AddressRepository addressRepository;
    private final OmnithequeRepository omnithequeRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder encoder;
    private final AddressService addressService;

    public UsersService(UsersMapper mapper, AddressMapper addressMapper, UsersRepository repository, AddressRepository addressRepository, OmnithequeRepository omnithequeRepository, RolesRepository rolesRepository, PasswordEncoder encoder, AddressService addressService) {
        this.mapper = mapper;
        this.addressMapper = addressMapper;
        this.repository = repository;
        this.addressRepository = addressRepository;
        this.omnithequeRepository = omnithequeRepository;
        this.rolesRepository = rolesRepository;
        this.encoder = encoder;
        this.addressService = addressService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws ConnectionErrorException {
        if(!repository.findByEmail(email).isPresent()) throw new UsernameNotFoundException("Une erreur est survenu lors de la connexion. veuillez verifier votre identifiant");
        return repository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("connexion impossible")
        );
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

        user.addRole(rolesRepository.findByName("USER"));
        return mapper.entityToDto(repository.save( user ));
    }

    public UsersDto getUser(String email) {
        return mapper.entityToDto(repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Une erreur est survenue")));
    }

    public UsersDto update(Authentication auth, UsersForm form){
        if(form == null )
            throw new IllegalArgumentException("le formulaire ne peuvent pas être null");

        Users user = repository.findByEmail(auth.getName()).get();
        if(form.getFirstName() != null) user.setFirstName(form.getFirstName());
        if(form.getLastName() != null) user.setLastName(form.getLastName());
        if(form.getBirthdate() != null) user.setBirthdate(form.getBirthdate());
        if(form.getPhone() != null) user.setPhone(form.getPhone());
        if(form.getEmail() != null) user.setEmail(form.getEmail());
        if(form.getPassword() != null) user.setPassword( encoder.encode(form.getPassword()) );

        if(form.getAddress() != null){
            Address addressChecked = addressService.search(form.getAddress());
            if(addressChecked != null ) {
                user.setAddress(addressChecked);
            }else {
                user.setAddress(addressRepository.save(addressMapper.formToEntity(form.getAddress())));
            }
        }
        return mapper.entityToDto(repository.save(user));
    }

    public Omnitheque addOmnitheque(Authentication auth, Omnitheque omnitheque) {
        Users user = repository.findByEmail(auth.getName()).get();
        user.setOmnitheque(omnithequeRepository.save(omnitheque));
        user.addRole(rolesRepository.findByName("PRO"));
        repository.save(user);
        return omnitheque;
    }
}
