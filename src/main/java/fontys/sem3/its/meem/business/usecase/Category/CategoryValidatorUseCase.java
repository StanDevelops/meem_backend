package fontys.sem3.its.meem.business.usecase.Category;

import fontys.sem3.its.meem.business.exception.InvalidIdentificationException;

public interface CategoryValidatorUseCase {
    Boolean validateId(int categoryId) throws InvalidIdentificationException;
}
