package be.digitalcity.projetspringrest.models.forms;

import be.digitalcity.projetspringrest.utils.Category;
import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductForm {
    private Long id;
    private String name;
    private Category category;
    private List<BorrowForm> borrowFormList;
    private List<OmnithequeForm> omnithequeFormList;

}
