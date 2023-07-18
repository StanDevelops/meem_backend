package fontys.sem3.its.meem.business.usecase.Commandment;

import fontys.sem3.its.meem.business.exception.InvalidIdentificationException;

public interface CommandmentValidatorUseCase {
    void validateId(int commandmentNr) throws InvalidIdentificationException;
}
