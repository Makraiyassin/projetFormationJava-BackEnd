package be.digitalcity.projetspringrest.controllers;

import be.digitalcity.projetspringrest.models.dtos.ProductDto;
import be.digitalcity.projetspringrest.models.forms.ProductForm;
import be.digitalcity.projetspringrest.services.ProductService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200/"})
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

    @PostMapping("/add")
    @Secured({"ROLE_PRO"})
    public ProductDto create(Authentication auth, @RequestParam(name = "omnithequeId") Long omnithequeId, @RequestBody ProductForm form){
        return service.create(auth, omnithequeId, form);
    }

    @PatchMapping()
    @Secured({"ROLE_PRO"})
    public ProductDto update(Long id, @RequestBody ProductForm form, Authentication auth){
        return service.update(id,form, auth);
    }

    @DeleteMapping()
    @Secured({"ROLE_PRO"})
    public ProductDto delete(Long id, Authentication auth){
        return service.delete(id, auth);
    }

}
