package be.digitalcity.projetspringrest.models.forms;

import lombok.*;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BorrowForm {
    @NotNull
    private LocalDate startBorrow;
    @NotNull
    @Future
    private LocalDate endBorrow;
    @NotNull
    private ProductForm product;
}
