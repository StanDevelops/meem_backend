package fontys.sem3.its.meem.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FrontendPostRatings {
    private int postId;
    private int upvoteCount;
    private int downvoteCount;
}