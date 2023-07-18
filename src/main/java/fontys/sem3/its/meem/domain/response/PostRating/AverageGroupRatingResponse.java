package fontys.sem3.its.meem.domain.response.PostRating;

import fontys.sem3.its.meem.domain.model.AverageGroupRating;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

//done
@Data
@Builder
@AllArgsConstructor
//@NoArgsConstructor
public class AverageGroupRatingResponse {
    private List<AverageGroupRating> statistics;

}
