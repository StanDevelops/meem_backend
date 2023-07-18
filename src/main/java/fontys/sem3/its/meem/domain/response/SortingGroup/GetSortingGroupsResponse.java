package fontys.sem3.its.meem.domain.response.SortingGroup;

import fontys.sem3.its.meem.domain.model.SortingGroup;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetSortingGroupsResponse {
    private List<SortingGroup> groups;
}
