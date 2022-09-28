package be.digitalcity.projetspringrest.services;

import be.digitalcity.projetspringrest.mappers.AddressMapper;
import be.digitalcity.projetspringrest.mappers.UsersMapper;
import be.digitalcity.projetspringrest.models.dtos.ProductDto;
import be.digitalcity.projetspringrest.models.dtos.UsersDto;
import be.digitalcity.projetspringrest.models.entities.Address;
import be.digitalcity.projetspringrest.models.entities.Omnitheque;
import be.digitalcity.projetspringrest.models.entities.Product;
import be.digitalcity.projetspringrest.models.entities.Users;
import be.digitalcity.projetspringrest.models.forms.ProductForm;
import be.digitalcity.projetspringrest.models.forms.UsersForm;
import be.digitalcity.projetspringrest.repositories.AddressRepository;
import be.digitalcity.projetspringrest.repositories.UsersRepository;
import be.digitalcity.projetspringrest.utils.JwtProperties;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
public class UsersDetailsServiceImpl implements UserDetailsService {

    private final UsersMapper mapper;
    private final AddressMapper addressMapper;
    private final UsersRepository repository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder encoder;
    private final AddressService addressService;
    private final JwtProperties jwtProperties;

    public UsersDetailsServiceImpl(UsersMapper mapper, AddressMapper addressMapper, UsersRepository repository, AddressRepository addressRepository, PasswordEncoder encoder, AddressService addressService, JwtProperties jwtProperties) {
        this.mapper = mapper;
        this.addressMapper = addressMapper;
        this.repository = repository;
        this.addressRepository = addressRepository;
        this.encoder = encoder;
        this.addressService = addressService;
        this.jwtProperties = jwtProperties;
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
        user.setRoles(List.of("USER"));
        return mapper.entityToDto(repository.save( user ));
    }

    public UsersDto getUserWithToken(HttpServletRequest request) {
        String token = request.getHeader(jwtProperties.getHeaderKey()).replace(jwtProperties.getHeaderPrefix(),"");
        DecodedJWT decodedJWT = JWT.decode(token);
        Optional<Users> user = repository.findByEmail(decodedJWT.getSubject() );
        if(user.isPresent()) return mapper.entityToDto( user.get() );
        return null;
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
        if(form.getAddress() != null) user.setAddress(addressMapper.formToEntity(form.getAddress()));
        if(form.getEmail() != null) user.setEmail(form.getEmail());
        if(form.getPassword() != null) user.setPassword(form.getPassword());

        return mapper.entityToDto(repository.save(user));
    }

    public Omnitheque addOmnitheque(Authentication auth, Omnitheque omnitheque) {
        Users user = repository.findByEmail(auth.getName()).get();
        user.setOmnitheque(omnitheque);
        return omnitheque;
    }
}
