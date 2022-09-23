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
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
    private String phone;
    private String email;
    private String password;
    @OneToOne
    @JoinColumn(name = "omnitheque_id")
    private Omnitheque omnitheque;
    @OneToMany(mappedBy = "user")
    private List<Borrow> borrowList;
}
