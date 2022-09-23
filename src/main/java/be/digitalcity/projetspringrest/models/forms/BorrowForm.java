package be.digitalcity.projetspringrest.models.forms;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BorrowForm {
    private Long id;
    private LocalDate startBorrow;
    private LocalDate endBorrow;
    private OmnithequeForm omnithequeForm;
    private UsersForm user;
    private List<ProductForm> productFormList;
}
