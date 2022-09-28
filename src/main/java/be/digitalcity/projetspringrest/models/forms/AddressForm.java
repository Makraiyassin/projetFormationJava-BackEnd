package be.digitalcity.projetspringrest.models.forms;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

public class AddressForm {
    @NotBlank
    @Size(min = 2,max = 255)
    private String street;
    @NotBlank
    @Max(99999)
    private Integer number;
    @NotBlank
    @Max(99999)
    private Integer cp;
    @NotBlank
    @Size(min = 2,max = 255)
    private String city;
    @NotBlank
    @Size(min = 2,max = 255)
    private String country;

    @Override
    public String toString() {
        return String.format("%s n°%s, %s %s", street,number,cp,city);
    }
}
