package be.digitalcity.projetspringrest.models.forms;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OmnithequeForm {
    @NotBlank
    private String name;
    @NotBlank
    @Pattern(regexp = "^(((\\+|00)32[ ]?(?:\\(0\\)[ ]?)?)|0){1}(4(60|[789]\\d)\\/?(\\s?\\d{2}\\.?){2}(\\s?\\d{2})|(\\d\\/?\\s?\\d{3}|\\d{2}\\/?\\s?\\d{2})(\\.?\\s?\\d{2}){2})$")
    private String phone;
    @NotBlank
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    private String email;
    @NotBlank
    private AddressForm address;
}
