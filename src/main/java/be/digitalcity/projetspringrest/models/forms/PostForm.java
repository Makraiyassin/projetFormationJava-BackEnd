package be.digitalcity.projetspringrest.models.forms;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostForm {
    @NotNull
    private Long id;
    @NotNull
    @Size(min = 2,max = 255)
    private String title;
    @NotNull
    private String image;
    @NotNull
    @Size(max = 20_000)
    private String content;
    @NotNull
    private LocalDate date;
    private Long omnithequeId;
}
