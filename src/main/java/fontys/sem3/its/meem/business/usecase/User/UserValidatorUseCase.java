package fontys.sem3.its.meem.business.usecase.User;

import fontys.sem3.its.meem.business.exception.InvalidIdentificationException;

import javax.validation.constraints.NotBlank;

public interface UserValidatorUseCase {
    boolean validateId(int userId) throws InvalidIdentificationException;

    boolean validateEmail(@NotBlank String email) throws InvalidIdentificationException;

    boolean validateUsername(@NotBlank String username) throws InvalidIdentificationException;

}
