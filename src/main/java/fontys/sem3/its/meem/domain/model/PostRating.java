package fontys.sem3.its.meem.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

//done
@Data
@Builder
@AllArgsConstructor
//@NoArgsConstructor
public class PostRating {
    private int ratingId;
    private int postId;
    private int userId;
    private int weight;
}
