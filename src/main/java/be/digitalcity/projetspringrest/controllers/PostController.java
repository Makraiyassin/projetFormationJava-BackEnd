package be.digitalcity.projetspringrest.controllers;

import be.digitalcity.projetspringrest.models.dtos.PostDto;
import be.digitalcity.projetspringrest.models.forms.PostForm;
import be.digitalcity.projetspringrest.services.PostService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200/","https://makraiyassin.github.io/"})
@RequestMapping("/api/post")
public class PostController {
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping("/{id:[0-9]+}")
    public PostDto getOne(@PathVariable long id){
        return service.getOne(id);
    }

    @GetMapping()
    public List<PostDto> getAll(){
        return service.getAll();
    }

    @GetMapping("/search")
    public List<PostDto> search(@RequestParam String word){
        return service.search(word);
    }

    @PostMapping("/create")
    @Secured({"ROLE_PRO","ROLE_ADMIN"})
    public PostDto create(Authentication auth,@Valid  @RequestBody PostForm form){
        return service.create(auth, form);
    }

    @PatchMapping("/update")
    @Secured({"ROLE_PRO","ROLE_ADMIN"})
    public PostDto update(Authentication auth,@Valid @RequestBody PostForm form){
        return service.update(auth, form);
    }

    @DeleteMapping("/delete")
    @Secured({"ROLE_PRO","ROLE_ADMIN"})
    public PostDto delete( @RequestParam(name = "id") Long id, Authentication auth){
        return service.delete(id, auth);
    }
}
