package fontys.sem3.its.meem.business.usecase.SortingGroup;

import fontys.sem3.its.meem.domain.response.SortingGroup.GetSortingGroupResponse;

//done
public interface GetSortingGroupUseCase {
//    GetSortingGroupResponse getSortingGroupById(int groupId);
    GetSortingGroupResponse getSortingGroupByName(String groupName);
//    SortingGroupEntity getSortingGroupEntityByName(String groupName);

}
