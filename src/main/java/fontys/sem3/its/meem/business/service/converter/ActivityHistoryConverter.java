package fontys.sem3.its.meem.business.service.converter;

import fontys.sem3.its.meem.domain.model.ActivityHistory;
import fontys.sem3.its.meem.persistence.entity.ActivityHistoryEntity;

public class ActivityHistoryConverter {
    public static ActivityHistory convert(ActivityHistoryEntity activityEntity) {
        return ActivityHistory.builder()
                .activityId(activityEntity.getActivityId())
                .postId(activityEntity.getPost().getPostId())
                .primaryUserId(activityEntity.getPrimaryUser().getUserId())
                .secondaryUserId(activityEntity.getSecondaryUser().getUserId())
                .commentId(activityEntity.getComment().getCommentId())
                .parentCommentId(activityEntity.getParentComment().getParentCommentId())
                .activityType(activityEntity.getActivityType())
                .activityTimestamp(activityEntity.getActivityTimestamp())
                .build();
    }

    //done
}
