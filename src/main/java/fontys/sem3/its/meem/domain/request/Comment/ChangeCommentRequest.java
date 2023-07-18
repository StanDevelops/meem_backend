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
public class ChangeCommentRequest {
    @NotNull
    private int commentId;
    @NotBlank
    private String newText;
}
