package be.digitalcity.projetspringrest.controllers;

import be.digitalcity.projetspringrest.models.dtos.TokenDto;
import be.digitalcity.projetspringrest.models.dtos.UsersDto;
import be.digitalcity.projetspringrest.models.forms.LoginForm;
import be.digitalcity.projetspringrest.models.forms.UsersForm;
import be.digitalcity.projetspringrest.services.UsersDetailsServiceImpl;
import be.digitalcity.projetspringrest.utils.JwtProvider;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@CrossOrigin(origins = {"http://localhost:4200/"})
@RequestMapping("/api/user")
public class UsersController {

    private final UsersDetailsServiceImpl service;
    private final AuthenticationManager authManager;
    private final JwtProvider jwtProvider;

    public UsersController(UsersDetailsServiceImpl service, AuthenticationManager authManager, JwtProvider jwtProvider) {
        this.service = service;
        this.authManager = authManager;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/register")
    public UsersDto createUser(@Valid @RequestBody UsersForm form){
        return service.create(form);
    }

    @PostMapping("/login")
    public TokenDto login(@Valid @RequestBody LoginForm form){
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(form.getEmail(), form.getPassword()));
        return jwtProvider.createToken(auth);
    }

    @GetMapping("/profil")
    @Secured("ROLE_USER")
    public UsersDto getUser(Authentication auth){
        return service.getUser(auth.getName());
    }

    @PatchMapping("/update")
    @Secured("ROLE_USER")
    public UsersDto updateUser(Authentication auth, @RequestBody UsersForm form){
        return service.update(auth, form);
    }
}
