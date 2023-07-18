package fontys.sem3.its.meem.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

//done
@Data
@Builder
@AllArgsConstructor
//@NoArgsConstructor
public class AverageGroupRating {
    @NotNull
    private double averageRating;
    @NotNull
    private int downvotesCount;
    @NotNull
    private int upvotesCount;
    @NotNull
    private int groupId;
    @NotBlank
    private String groupName;
}
