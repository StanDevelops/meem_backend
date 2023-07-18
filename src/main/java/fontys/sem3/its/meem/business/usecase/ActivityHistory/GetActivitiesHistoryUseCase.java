package fontys.sem3.its.meem.business.usecase.ActivityHistory;

import fontys.sem3.its.meem.domain.model.ActivityTypeEnum;
import fontys.sem3.its.meem.domain.response.ActivityHistory.GetActivitiesHistoryResponse;

public interface GetActivitiesHistoryUseCase {
    GetActivitiesHistoryResponse getActivitiesByUserId(int userId);

    GetActivitiesHistoryResponse getActivitiesByUserIdAndType(int userId, ActivityTypeEnum activityType);
}

