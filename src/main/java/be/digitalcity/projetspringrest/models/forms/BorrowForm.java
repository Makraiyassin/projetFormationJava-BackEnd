package be.digitalcity.projetspringrest.models.forms;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BorrowForm {
    private LocalDate startBorrow;
    private LocalDate endBorrow;
    private List<ProductForm> productFormList;
}
