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
    private String image;


    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "omnitheque")
    private List<Borrow> borrowList;

    @OneToMany(mappedBy = "omnitheque")
    private List<Product> productList;

    @OneToMany(mappedBy = "omnitheque", cascade = CascadeType.ALL)
    private List<Post> postList;

}
