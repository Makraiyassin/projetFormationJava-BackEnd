package be.digitalcity.projetspringrest.models.forms;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsersForm {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private AddressForm address;
    private String phone;
    private String email;
    private String password;
    private OmnithequeForm omnitheque;
    private List<BorrowForm> borrowList;
}
