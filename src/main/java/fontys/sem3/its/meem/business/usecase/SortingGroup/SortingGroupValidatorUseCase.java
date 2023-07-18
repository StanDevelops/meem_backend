package fontys.sem3.its.meem.business.usecase.SortingGroup;

import fontys.sem3.its.meem.business.exception.InvalidIdentificationException;

public interface SortingGroupValidatorUseCase {
    Boolean validateId(int groupId) throws InvalidIdentificationException;
}
