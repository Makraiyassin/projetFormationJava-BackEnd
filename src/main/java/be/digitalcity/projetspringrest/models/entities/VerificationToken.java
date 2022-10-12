package be.digitalcity.projetspringrest.models.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne(targetEntity = Users.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private Users user;

    private LocalDate expiryDate;

    private LocalDate calculateExpiryDate(int expiryTimeInMinutes) {
        return LocalDate.now().plusDays(1);
    }

    // standard constructors, getters and setters
}