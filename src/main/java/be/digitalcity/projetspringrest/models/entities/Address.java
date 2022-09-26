package be.digitalcity.projetspringrest.models.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private int number;
    private int cp;
    private String city;
    private String country;

    @Override
    public String toString() {
        return String.format("%s n°%s, %s %s", street,number,cp,city);
    }

}
