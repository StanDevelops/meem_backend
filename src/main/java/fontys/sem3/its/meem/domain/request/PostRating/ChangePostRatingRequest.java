package fontys.sem3.its.meem.domain.request.PostRating;

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
public class ChangePostRatingRequest {
    @NotNull
    private int userId;
    @NotNull
    private int ratingId;
    @NotNull
    private int postId;
    @NotNull
    private int newRatingWeight;

}
