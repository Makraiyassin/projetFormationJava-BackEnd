package be.digitalcity.projetspringrest.controllers;


import be.digitalcity.projetspringrest.models.dtos.OmnithequeDto;
import be.digitalcity.projetspringrest.services.OmnithequeService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200/"})
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

}
