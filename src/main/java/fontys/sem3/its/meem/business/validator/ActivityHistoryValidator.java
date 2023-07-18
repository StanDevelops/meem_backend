package fontys.sem3.its.meem.business.validator;

import fontys.sem3.its.meem.business.exception.InvalidIdentificationException;
import fontys.sem3.its.meem.business.usecase.ActivityHistory.ActivityHistoryValidatorUseCase;
import fontys.sem3.its.meem.persistence.repository.ActivityHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ActivityHistoryValidator implements ActivityHistoryValidatorUseCase {
    private final ActivityHistoryRepository activityHistoryRepository;

    @Override
    public void validateId(int activityId) {
//check if entity ID is null or not belonging to DB entity
        if (activityId == 0 || !activityHistoryRepository.existsByActivityId(activityId)) {  //check if entity ID is null or not belonging to DB entity
            throw new InvalidIdentificationException(); //NO MATCH! NOT GOOD! Throw a hissy!
        }
    }

}
