package be.digitalcity.projetspringrest.models.entities;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate startBorrow;
    private LocalDate endBorrow;

    @ManyToOne
    private Product product;
    @ManyToOne
    private Omnitheque omnitheque;
    @ManyToOne
    private Users user;
}
