package be.digitalcity.projetspringrest.models.entities;

import lombok.*;
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
    @JoinTable(
            name = "omnitheque_borrow",
            joinColumns = @JoinColumn(name = "omnitheque_id"),
            inverseJoinColumns=@JoinColumn(name="borrow_id")
    )
    private List<Borrow> borrowList;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "omnitheque_product",
            joinColumns = @JoinColumn(name = "omnitheque_id"),
            inverseJoinColumns=@JoinColumn(name="product_id")
    )
    private List<Product> productList;

    public void addProduct(Product product) {
        this.productList.add(product);
    }
}
