package be.digitalcity.projetspringrest.models.forms;

import be.digitalcity.projetspringrest.utils.Category;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductForm {
    @NotNull
    private Long id;
    @NotNull
    @Size(min = 2,max = 255)
    private String name;
    @NotNull
    private Category category;
    private Integer quantity;
    @NotNull
    private String image;
    @NotNull
    private String description;

}
