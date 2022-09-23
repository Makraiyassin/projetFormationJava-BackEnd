package be.digitalcity.projetspringrest.models.dtos;

import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OmnithequeDto {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private AddressDto address;
    private Long ownerId;
    private List<Long> borrowIdList;
    private List<ProductDto> productList;
}
