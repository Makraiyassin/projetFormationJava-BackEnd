package be.digitalcity.projetspringrest.models.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
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
    @JoinColumn(name = "omnitheque_id")
    private Omnitheque omnitheque;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToMany
    @JoinTable(
            name = "borrow_product",
            joinColumns = @JoinColumn(name = "borrow_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> productList;
}
