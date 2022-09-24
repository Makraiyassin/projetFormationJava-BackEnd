package be.digitalcity.projetspringrest.models.forms;

import be.digitalcity.projetspringrest.utils.Category;
import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductForm {
    private String name;
    private Category category;
}
