package fontys.sem3.its.meem.domain.response.ActivityHistory;

import fontys.sem3.its.meem.domain.model.ActivityHistory;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetActivitiesHistoryResponse {
    List<ActivityHistory> activityHistoryList;
}
