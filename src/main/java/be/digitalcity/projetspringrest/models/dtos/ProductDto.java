package be.digitalcity.projetspringrest.models.dtos;

import be.digitalcity.projetspringrest.utils.Category;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDto {
    private Long id;
    private String name;
    private Category category;
    private int quantity;
    private String image;
    private String description;
    private Long omnithequeId;
}
