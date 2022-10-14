package be.digitalcity.projetspringrest.models.forms;

import be.digitalcity.projetspringrest.utils.Category;
import lombok.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductForm {
    private Long id;
    @NotNull
    @Size(min = 2,max = 255)
    private String name;
    @NotNull
    private Category category;
    private Integer quantity;
    private String image;
    @NotNull
    @Size(max = 2_000)
    private String description;
    private Long omnithequeId;
}
