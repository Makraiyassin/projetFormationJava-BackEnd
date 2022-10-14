package be.digitalcity.projetspringrest.controllers;


import be.digitalcity.projetspringrest.models.dtos.OmnithequeDto;
import be.digitalcity.projetspringrest.models.forms.OmnithequeForm;
import be.digitalcity.projetspringrest.services.OmnithequeService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200/","https://makraiyassin.github.io/","https://omnitheque.herokuapp.com/"})
@RequestMapping("/api/omnitheque")
public class OmnithequeController {
    private final OmnithequeService service;

    public OmnithequeController(OmnithequeService service) {
        this.service = service;
    }
    @GetMapping("/{id:[0-9]+}")
    public OmnithequeDto getOne(@PathVariable long id){
        return service.getOne(id);
    }

    @GetMapping()
    public List<OmnithequeDto> getAll(){
        return service.getAll();
    }

    @PostMapping("/create")
    @Secured("ROLE_USER")
    public OmnithequeDto Create(@Valid @RequestBody OmnithequeForm form, Authentication auth){
        return service.create(form, auth);
    }
    @PatchMapping("/update")
    @Secured("ROLE_USER")
    public OmnithequeDto updateOmnitheque(Authentication auth, @RequestBody OmnithequeForm form){
        return service.update(auth, form);
    }
}
