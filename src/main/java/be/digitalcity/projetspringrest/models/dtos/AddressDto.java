package be.digitalcity.projetspringrest.models.dtos;

import lombok.*;
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddressDto {
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
