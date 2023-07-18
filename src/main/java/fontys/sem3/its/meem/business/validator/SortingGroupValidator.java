package fontys.sem3.its.meem.business.validator;

import fontys.sem3.its.meem.business.exception.InvalidIdentificationException;
import fontys.sem3.its.meem.business.usecase.SortingGroup.SortingGroupValidatorUseCase;
import fontys.sem3.its.meem.persistence.repository.SortingGroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SortingGroupValidator implements SortingGroupValidatorUseCase {
    private final SortingGroupRepository sortingGroupRepository;

    @Override
    public Boolean validateId(int groupId) {
        if (groupId == 0 || !sortingGroupRepository.existsByGroupId(groupId)) {  //check if entity ID is null or not belonging to DB entity
            throw new InvalidIdentificationException(); //NO MATCH! NOT GOOD! Throw a hissy!
        }
        return true;
    }
}
