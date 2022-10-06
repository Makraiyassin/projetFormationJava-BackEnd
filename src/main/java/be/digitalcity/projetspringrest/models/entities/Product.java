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

    @OneToMany(mappedBy = "product")
    private List<Borrow> borrowList;

    @ManyToOne(cascade = CascadeType.ALL)
    private Omnitheque omnitheque;
}
