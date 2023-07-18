package fontys.sem3.its.meem.domain.request.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreatePostRequest {
    @NotNull
    private int authorId;
    @NotNull
    private int categoryId;
    @NotBlank
    @Length(min = 2, max = 140)
    private String postTitle;
    @NotBlank
    private String mediaAddress;
    @NotNull
    private Boolean explicit;
}
