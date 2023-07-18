package fontys.sem3.its.meem.domain.response.PostRating;

import fontys.sem3.its.meem.domain.model.PostRating;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetPostRatingsResponse {
    private List<PostRating> postRatings;
}