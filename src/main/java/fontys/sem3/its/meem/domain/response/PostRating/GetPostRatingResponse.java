package fontys.sem3.its.meem.domain.response.PostRating;

import fontys.sem3.its.meem.domain.model.PostRating;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetPostRatingResponse {
    private PostRating postRating;
}
