package fontys.sem3.its.meem.business.usecase.Ban;

import fontys.sem3.its.meem.business.exception.InvalidIdentificationException;

import javax.validation.constraints.NotNull;

public interface BanValidatorUseCase {
    void validateId(@NotNull int banId) throws InvalidIdentificationException;
}
