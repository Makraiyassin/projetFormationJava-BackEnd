package be.digitalcity.projetspringrest.controllers;


import be.digitalcity.projetspringrest.models.dtos.OmnithequeDto;
import be.digitalcity.projetspringrest.services.OmnithequeService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/omnitheque")
public class OmnithequeController {
    private final OmnithequeService service;
    public OmnithequeController(OmnithequeService service) {
        this.service = service;
    }

    @GetMapping("/{id:[0-9]+}")
    public List<OmnithequeDto> getOne(@PathVariable long id){
        return new ArrayList<>();
    }

    @GetMapping()
    public List<OmnithequeDto> getAll(){
        return service.getAll();
    }

}
