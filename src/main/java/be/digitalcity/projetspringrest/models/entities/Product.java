package be.digitalcity.projetspringrest.models.entities;

import be.digitalcity.projetspringrest.utils.Category;
import lombok.*;

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

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToMany(mappedBy = "productList")
    private List<Borrow> borrowList;

    @ManyToMany(mappedBy = "productList")
    private List<Omnitheque> omnithequeList;
}
