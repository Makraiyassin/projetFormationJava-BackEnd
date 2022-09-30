package be.digitalcity.projetspringrest.models.forms;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
    private LocalDate birthdate;
    @NotNull
    private AddressForm address;
    @NotNull
    @Pattern(regexp = "^(((\\+|00)32[ ]?(?:\\(0\\)[ ]?)?)|0){1}(4(60|[789]\\d)\\/?(\\s?\\d{2}\\.?){2}(\\s?\\d{2})|(\\d\\/?\\s?\\d{3}|\\d{2}\\/?\\s?\\d{2})(\\.?\\s?\\d{2}){2})$")
    private String phone;
    @NotNull
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    private String email;
    @NotNull
    @Size(min = 2,max = 255)
    private String password;
}

