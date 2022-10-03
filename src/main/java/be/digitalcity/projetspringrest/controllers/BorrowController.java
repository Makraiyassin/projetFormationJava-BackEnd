package be.digitalcity.projetspringrest.controllers;

import be.digitalcity.projetspringrest.models.dtos.BorrowDto;
import be.digitalcity.projetspringrest.models.dtos.OmnithequeDto;
import be.digitalcity.projetspringrest.services.BorrowService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:4200/"})
@RequestMapping("/api/borrow")
public class BorrowController {
    private BorrowService service;

    public BorrowController(BorrowService service) {
        this.service = service;
    }

    @PostMapping("one")
    @Secured("ROLE_USER")
    public BorrowDto Create(Authentication auth, @RequestParam Long omnithequeId, @RequestParam Long productId){
        return service.create(auth, omnithequeId, productId);
    }

}
