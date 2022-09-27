package be.digitalcity.projetspringrest.models.entities;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users implements UserDetails {
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
    private boolean enabled = true;

    @OneToOne
    @JoinColumn(name = "omnitheque_id")
    private Omnitheque omnitheque;

    @OneToMany
    @JoinTable(name = "users_borrow")
    private List<Borrow> borrowList;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map((role) -> new SimpleGrantedAuthority("ROLE_"+role))
                .toList();
    }
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
