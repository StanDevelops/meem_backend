package fontys.sem3.its.meem.domain.response.SortingGroup;

import fontys.sem3.its.meem.domain.model.SortingGroup;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetSortingGroupResponse {
    private SortingGroup group;
}
