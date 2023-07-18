package fontys.sem3.its.meem.business.usecase.SortingGroup;

import fontys.sem3.its.meem.domain.request.SortingGroup.CreateSortingGroupRequest;
import fontys.sem3.its.meem.persistence.entity.SortingGroupEntity;

public interface CreateSortingGroupUseCase {
    SortingGroupEntity createSortingGroup(CreateSortingGroupRequest request);
}
