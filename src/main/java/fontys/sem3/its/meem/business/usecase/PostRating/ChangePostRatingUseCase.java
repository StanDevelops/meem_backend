package fontys.sem3.its.meem.business.usecase.PostRating;

import fontys.sem3.its.meem.business.exception.InvalidIdentificationException;
import fontys.sem3.its.meem.business.exception.NonExistingRatingException;
import fontys.sem3.its.meem.business.exception.RedundantChangeException;
import fontys.sem3.its.meem.business.exception.UnauthorizedDataAccessException;
import fontys.sem3.its.meem.domain.model.FrontendPostRatings;
import fontys.sem3.its.meem.domain.request.PostRating.ChangePostRatingRequest;

public interface ChangePostRatingUseCase {
    //    FrontendPostRatings changePostRating(int userId, int postId, int newRatingWeight);

    /**
     * @param request
     * @return FrontendPostRatings - a combined count of upvotes and downvotes
     * @should throw InvalidIdentificationException_ when post validation fails
     * @should throw InvalidIdentificationException_ when user validation fails
     * @should throw UnauthorizedDataAccessException_ when user is banned
     * @should throw UnauthorizedDataAccessException_ when request user different from access token user
     * @should throw NonExistingRatingException _when rating with given ids does not exist
     * @should throw RedundantChangeException _when rating with given id and weight exists
     */
    FrontendPostRatings changePostRating(ChangePostRatingRequest request) throws UnauthorizedDataAccessException,
            InvalidIdentificationException, NonExistingRatingException, RedundantChangeException;
}

