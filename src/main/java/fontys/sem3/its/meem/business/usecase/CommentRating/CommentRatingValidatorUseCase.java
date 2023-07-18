package fontys.sem3.its.meem.business.usecase.CommentRating;

import fontys.sem3.its.meem.business.exception.InvalidIdentificationException;

import javax.validation.constraints.NotNull;

public interface CommentRatingValidatorUseCase {
    void validateId(@NotNull int commentId, @NotNull int userId) throws InvalidIdentificationException;
}
