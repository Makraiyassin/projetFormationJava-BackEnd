package be.digitalcity.projetspringrest.models.forms;

import lombok.*;
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressForm {
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
