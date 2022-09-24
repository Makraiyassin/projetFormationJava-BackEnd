package be.digitalcity.projetspringrest.models.forms;

import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OmnithequeForm {
    private String name;
    private String phone;
    private String email;
    private AddressForm addressForm;
}
