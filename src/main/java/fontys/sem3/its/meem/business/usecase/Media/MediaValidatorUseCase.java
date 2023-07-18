package fontys.sem3.its.meem.business.usecase.Media;

import fontys.sem3.its.meem.business.exception.InvalidIdentificationException;

public interface MediaValidatorUseCase {
    void validateId(int mediaId) throws InvalidIdentificationException;

    void validateAddress(String mediaAddress) throws InvalidIdentificationException;
}
