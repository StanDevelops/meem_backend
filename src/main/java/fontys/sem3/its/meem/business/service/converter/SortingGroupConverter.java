package fontys.sem3.its.meem.business.service.converter;

import fontys.sem3.its.meem.domain.model.SortingGroup;
import fontys.sem3.its.meem.persistence.entity.SortingGroupEntity;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SortingGroupConverter {
    public static SortingGroup convert(SortingGroupEntity sortingGroupEntity) {
        return SortingGroup.builder()
                .groupId(sortingGroupEntity.getGroupId())
                .groupName(sortingGroupEntity.getGroupName())
                .groupRank(sortingGroupEntity.getGroupRank())
                .build();
    }

    //done
}
