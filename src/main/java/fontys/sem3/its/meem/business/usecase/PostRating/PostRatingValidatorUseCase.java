package fontys.sem3.its.meem.business.usecase.PostRating;

import fontys.sem3.its.meem.business.exception.NonExistingRatingException;

import javax.validation.constraints.NotNull;

public interface PostRatingValidatorUseCase {
    boolean validateId(@NotNull int postId, @NotNull int userId) throws NonExistingRatingException;

    boolean validateId(@NotNull int ratingId) throws NonExistingRatingException;

}
