package be.digitalcity.projetspringrest.models.entities;

import be.digitalcity.projetspringrest.utils.Category;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int quantity;
    private String image;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String description;
    @OneToMany(orphanRemoval = true)
    @JoinTable(
            name = "borrow_product",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "borrow_id")
    )
    private List<Borrow> borrowList;

    @ManyToOne
    private Omnitheque omnitheque;
}
