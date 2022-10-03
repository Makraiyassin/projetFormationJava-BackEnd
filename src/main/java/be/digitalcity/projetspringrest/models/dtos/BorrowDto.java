package be.digitalcity.projetspringrest.models.dtos;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BorrowDto {
    private Long id;
    private LocalDate startBorrow;
    private LocalDate endBorrow;
    private List<Long> productIdList;
    private Long OmnithequeId;
    private Long userId;
}
