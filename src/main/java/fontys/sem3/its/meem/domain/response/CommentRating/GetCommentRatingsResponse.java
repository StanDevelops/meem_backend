package fontys.sem3.its.meem.domain.response.CommentRating;

import fontys.sem3.its.meem.domain.model.CommentRating;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetCommentRatingsResponse {
    private List<CommentRating> commentRatingsList;
}
