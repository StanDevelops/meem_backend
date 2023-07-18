package fontys.sem3.its.meem.domain.request.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentRequest {
    @NotNull
    private int authorId;
    @NotNull
    private int postId;
    @NotBlank
    private String commentBody;
    @NotNull
    private Boolean explicit;
}
