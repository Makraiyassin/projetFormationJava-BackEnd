package be.digitalcity.projetspringrest.models.forms;

import lombok.*;
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddressForm {
    private Long id;
    private String street;
    private int number;
    private int cp;
    private String city;
    private String country;

}
