package be.digitalcity.projetspringrest.services;

import be.digitalcity.projetspringrest.mappers.UsersMapper;
import be.digitalcity.projetspringrest.models.entities.Users;
import be.digitalcity.projetspringrest.models.forms.UsersForm;
import be.digitalcity.projetspringrest.repositories.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersDetailsServiceImpl implements UserDetailsService {

    private final UsersMapper mapper;
    private final UsersRepository repository;
    private final PasswordEncoder encoder;

    public UsersDetailsServiceImpl(UsersMapper mapper, UsersRepository repository, PasswordEncoder encoder) {
        this.mapper = mapper;
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("connexion impossible"));
    }

    public void create(UsersForm form){
        Users user = mapper.formToEntity(form); //mapper en entity
        user.setPassword( encoder.encode(user.getPassword()) );
        repository.save( user );
    }
}
