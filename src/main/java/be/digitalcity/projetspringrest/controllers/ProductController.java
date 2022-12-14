package be.digitalcity.projetspringrest.controllers;

import be.digitalcity.projetspringrest.models.dtos.ProductDto;
import be.digitalcity.projetspringrest.models.forms.ProductForm;
import be.digitalcity.projetspringrest.services.ProductService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200/","https://makraiyassin.github.io/","https://omnitheque.herokuapp.com/"})
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/{id:[0-9]+}")
    public ProductDto getOne(@PathVariable long id){
        return service.getOne(id);
    }

    @GetMapping()
    public List<ProductDto> getAll(){
        return service.getAll();
    }

    @GetMapping("/search")
    public List<ProductDto> search(@RequestParam String word){
        return service.search(word);
    }

    @PostMapping("/create")
    @Secured({"ROLE_PRO","ROLE_ADMIN"})
    public ProductDto create(Authentication auth,@Valid  @RequestBody ProductForm form){
        return service.create(auth, form);
    }

    @PatchMapping("/update")
    @Secured({"ROLE_PRO","ROLE_ADMIN"})
    public ProductDto update(Authentication auth,@Valid @RequestBody ProductForm form){
        return service.update(auth, form);
    }

    @DeleteMapping("/delete")
    @Secured({"ROLE_PRO","ROLE_ADMIN"})
    public ProductDto delete( @RequestParam(name = "id") Long id, Authentication auth){
        return service.delete(id, auth);
    }
}
