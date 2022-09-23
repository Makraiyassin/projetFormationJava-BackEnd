package be.digitalcity.projetspringrest.models.dtos;

import be.digitalcity.projetspringrest.utils.Category;
import lombok.*;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDto {
    private Long id;
    private String name;
    private Category category;
//    private List<BorrowDto> borrowDtoList;
//    private List<OmnithequeDto> omnithequeDtoList;

}
