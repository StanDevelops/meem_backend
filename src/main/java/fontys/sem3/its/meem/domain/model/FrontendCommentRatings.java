package fontys.sem3.its.meem.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FrontendCommentRatings {
    private int commentId;
    private int upvoteCount;
    private int downvoteCount;
}