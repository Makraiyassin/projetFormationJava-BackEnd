package be.digitalcity.projetspringrest.models.dtos;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private String image;
    private LocalDate date;
    private Long omnithequeId;
}
