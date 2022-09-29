package be.digitalcity.projetspringrest.models.entities;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Omnitheque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;
    private String email;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany
    @JoinTable(name = "users_borrow")
    private List<Borrow> borrowList;


    @ManyToMany
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @JoinTable(
            name = "omnitheque_product",
            joinColumns = @JoinColumn(name = "omnitheque_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> productList;

    public void addProduct(Product product) {
        this.productList.add(product);
    }
}
