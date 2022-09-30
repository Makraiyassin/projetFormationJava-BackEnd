package be.digitalcity.projetspringrest.models.forms;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

public class AddressForm {
    @NotNull
    @Size(min = 2,max = 255)
    private String street;
    @NotNull
    @Max(99999)
    private Integer number;
    @NotNull
    @Max(99999)
    private Integer cp;
    @NotNull
    @Size(min = 2,max = 255)
    private String city;
    @NotNull
    @Size(min = 2,max = 255)
    private String country;

    @Override
    public String toString() {
        return String.format("%s n°%s, %s %s", street,number,cp,city);
    }
}
