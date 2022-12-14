package be.digitalcity.projetspringrest.models.dtos;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsersDto {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private AddressDto address;
    private String phone;
    private String email;
    private String password;
    private OmnithequeDto omnitheque;
    private List<BorrowDto> borrowList;
}
