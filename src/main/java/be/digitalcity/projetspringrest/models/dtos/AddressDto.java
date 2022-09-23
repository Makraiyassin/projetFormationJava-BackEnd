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
}
