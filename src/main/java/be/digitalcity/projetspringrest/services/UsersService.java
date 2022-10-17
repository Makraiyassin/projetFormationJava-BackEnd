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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;


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
    private final JavaMailSender mailSender;

    public UsersService(UsersMapper mapper, AddressMapper addressMapper, UsersRepository repository, AddressRepository addressRepository, OmnithequeRepository omnithequeRepository, RolesRepository rolesRepository, PasswordEncoder encoder, AddressService addressService, JavaMailSender mailSender) {
        this.mapper = mapper;
        this.addressMapper = addressMapper;
        this.repository = repository;
        this.addressRepository = addressRepository;
        this.omnithequeRepository = omnithequeRepository;
        this.rolesRepository = rolesRepository;
        this.encoder = encoder;
        this.addressService = addressService;
        this.mailSender = mailSender;
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
        Users user = repository.findByEmail(auth.getName()).orElseThrow(
                ()->new EntityNotFoundException("aucun utilisateur trouvé")
        );
        user.setOmnitheque(omnithequeRepository.save(omnitheque));
        user.addRole(rolesRepository.findByName("PRO"));
        repository.save(user);
        return omnitheque;
    }

    public UsersDto getInfos(Long id) {
        UsersDto usersDto = new UsersDto();

        Users user = repository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("aucun utilisateur trouvé avec l'id {"+id+"}")
        );

        usersDto.setFirstName(user.getFirstName());
        usersDto.setLastName(user.getLastName());
        usersDto.setEmail(user.getEmail());
        usersDto.setPhone(user.getPhone());
        usersDto.setAddress(addressMapper.entityToDto(user.getAddress()));

        return usersDto;
    }

    public void requestResetPassword(String urlResetPassword, String email) {
        Users user = repository.findByEmail(email).orElseThrow(
                ()-> new EntityNotFoundException("aucun utilisateur est lié à l'email {"+email+"}")
        );

        String token = UUID.randomUUID().toString();
        user.setTokenResetPassword(token);
        repository.save(user);

        String content = "cher(e) "+user.getFirstName()+",<br>"
                + "Cliquez sur le lien suivant:<br>"
                + "<h3><a href=\""+urlResetPassword+token+"\">Réinitialiser mot de passe</a></h3>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom("m.hellodev@gmail.com", "l'Omnitheque");
            helper.setTo(email);
            helper.setSubject("Réinitialisation du mot de passe");
            helper.setText(content, true);

        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        mailSender.send(message);
    }

    public void resetPassword(String token, UsersForm form) {
        Users user = repository.findByTokenResetPassword(token).orElseThrow(
                ()-> new EntityNotFoundException("Votre token est invalide")
        );
        user.setPassword(encoder.encode(form.getPassword()));
        repository.save(user);
    }
}
