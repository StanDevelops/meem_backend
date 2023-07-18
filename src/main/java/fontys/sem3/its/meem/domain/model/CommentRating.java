package fontys.sem3.its.meem.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

//done
@Data
@Builder
@AllArgsConstructor
//@NoArgsConstructor
public class CommentRating {
    private int ratingId;
    private int commentId;
    private int userId;
    private int weight;
}
