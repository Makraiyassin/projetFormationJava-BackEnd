package be.digitalcity.projetspringrest.controllers;

import be.digitalcity.projetspringrest.models.dtos.TokenDto;
import be.digitalcity.projetspringrest.models.dtos.UsersDto;
import be.digitalcity.projetspringrest.models.forms.LoginForm;
import be.digitalcity.projetspringrest.models.forms.UsersForm;
import be.digitalcity.projetspringrest.services.UsersService;
import be.digitalcity.projetspringrest.utils.JwtProvider;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@CrossOrigin(origins = {"http://localhost:4200/","https://makraiyassin.github.io/","https://omnitheque.herokuapp.com/"})
@RequestMapping("/api/user")
public class UsersController {

    private final UsersService service;
    private final AuthenticationManager authManager;
    private final JwtProvider jwtProvider;

    public UsersController(UsersService service, AuthenticationManager authManager, JwtProvider jwtProvider) {
        this.service = service;
        this.authManager = authManager;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/register")
    public UsersDto createUser(@Valid @RequestBody UsersForm form){
        System.out.println(form);
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
    @GetMapping("/infos")
    @Secured("ROLE_USER")
    public UsersDto getInfos(@RequestParam Long id){
        return service.getInfos(id);
    }


    @GetMapping("/requestResetPassword")
    public void requestResetPassword(@RequestParam String urlResetPassword, @RequestParam String email){
        service.requestResetPassword(urlResetPassword,email);
    }
    @PostMapping("/resetPassword")
    public void resetPassword( @RequestParam String token, @RequestBody UsersForm form){
        service.resetPassword(token, form);
    }

}
