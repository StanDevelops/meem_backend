package fontys.sem3.its.meem.business.usecase.ActivityHistory;

import fontys.sem3.its.meem.business.exception.InvalidIdentificationException;

public interface ActivityHistoryValidatorUseCase {
    void validateId(int activityId) throws InvalidIdentificationException;

}
