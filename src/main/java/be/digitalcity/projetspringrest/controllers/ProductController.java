package be.digitalcity.projetspringrest.controllers;

import be.digitalcity.projetspringrest.models.dtos.ProductDto;
import be.digitalcity.projetspringrest.services.ProductService;
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

}
