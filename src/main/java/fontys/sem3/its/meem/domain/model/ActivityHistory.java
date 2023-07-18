package fontys.sem3.its.meem.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

//done
@Data
@Builder
@AllArgsConstructor
//@NoArgsConstructor
public class ActivityHistory {
    private int activityId;
    private int postId;
    private int primaryUserId;
    private int secondaryUserId;
    private int commentId;
    private int parentCommentId;
    private ActivityTypeEnum activityType;
    private Timestamp activityTimestamp;
}
