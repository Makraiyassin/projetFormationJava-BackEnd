package be.digitalcity.projetspringrest.models.forms;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginForm {
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
