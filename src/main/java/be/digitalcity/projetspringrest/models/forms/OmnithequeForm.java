package be.digitalcity.projetspringrest.models.forms;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OmnithequeForm {
    private Long id;
    @NotNull
    @Size(min = 2,max = 255)
    private String name;
    @NotNull
    @Pattern(
            regexp = "^(((\\+|00)32[ ]?(?:\\(0\\)[ ]?)?)|0){1}(4(60|[789]\\d)\\/?(\\s?\\d{2}\\.?){2}(\\s?\\d{2})|(\\d\\/?\\s?\\d{3}|\\d{2}\\/?\\s?\\d{2})(\\.?\\s?\\d{2}){2})$",
            message = "numéro téléphone invalide"
    )
    private String phone;
    @NotNull
    @Email(message = "addresse email incorrect")
    private String email;
    @NotNull
    private AddressForm address;
    private String image;
}
