package fontys.sem3.its.meem.domain.request.CommentRating;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

//done
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeCommentRatingRequest {
    @NotNull
    private int userId;
    @NotNull
    private int commentId;
    @NotNull
    private int newRatingWeight;

}
