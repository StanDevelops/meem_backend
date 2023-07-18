package fontys.sem3.its.meem.domain.response.PostRating;

import fontys.sem3.its.meem.domain.model.FrontendPostRatings;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePostRatingResponse {
    private FrontendPostRatings postRatings;
    private int ratingId;
}
