package be.digitalcity.projetspringrest.models.dtos;

import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class OmnithequeDto {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private AddressDto address;
    private List<BorrowDto> borrowList;
    private List<ProductDto> productList;
}
