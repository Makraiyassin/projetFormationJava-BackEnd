package be.digitalcity.projetspringrest.models.forms;

import lombok.*;

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
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    private String email;
    @NotNull
    @Size(min = 2,max = 255)
    private String password;
}
