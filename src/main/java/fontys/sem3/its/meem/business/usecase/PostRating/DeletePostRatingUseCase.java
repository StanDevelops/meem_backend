package fontys.sem3.its.meem.business.usecase.PostRating;

import fontys.sem3.its.meem.business.exception.InvalidIdentificationException;
import fontys.sem3.its.meem.business.exception.NonExistingRatingException;
import fontys.sem3.its.meem.business.exception.UnauthorizedDataAccessException;
import fontys.sem3.its.meem.domain.model.FrontendPostRatings;

import javax.validation.constraints.NotNull;

public interface DeletePostRatingUseCase {
    /**
     * @param postId
     * @param userId
     * @return FrontendPostRatings - a combined count of upvotes and downvotes
     * @should delete post rating and return the updated count of the post ratings_ when checks pass
     * @should throw InvalidIdentificationException_ when user validation fails
     * @should throw InvalidIdentificationException_ when post validation fails
     * @should throw NonExistingRatingException _when rating with given ids does not exist
     * @should throw UnauthorizedDataAccessException_ when request user different from access token user
     */
    FrontendPostRatings deletePostRating(@NotNull int ratingId, @NotNull int postId, @NotNull int userId) throws InvalidIdentificationException,
            NonExistingRatingException, UnauthorizedDataAccessException;
}
