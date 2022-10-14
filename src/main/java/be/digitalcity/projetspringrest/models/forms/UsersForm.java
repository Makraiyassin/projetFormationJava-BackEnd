package be.digitalcity.projetspringrest.models.forms;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsersForm {
    @NotNull
    @Size(min = 2,max = 255)
    private String firstName;
    @NotNull
    @Size(min = 2,max = 255)
    private String lastName;
    @NotNull
    @Past
    private LocalDate birthdate;
    @NotNull
    private AddressForm address;
    @NotNull
    @Pattern(
            regexp = "^(((\\+|00)32[ ]?(?:\\(0\\)[ ]?)?)|0){1}(4(60|[789]\\d)\\/?(\\s?\\d{2}\\.?){2}(\\s?\\d{2})|(\\d\\/?\\s?\\d{3}|\\d{2}\\/?\\s?\\d{2})(\\.?\\s?\\d{2}){2})$",
            message = "numéto de telephone incorrect"
    )
    private String phone;
    @NotNull
    @Email(message = "addresse email incorrect")
    private String email;
    @NotNull
    @Size(min = 6,max = 255)
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$",
            message = "le mot de passe doit contenir 6 carractère dont au moins une lettre et un chiffre"
    )
    private String password;
}

