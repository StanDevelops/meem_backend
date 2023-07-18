package fontys.sem3.its.meem.business.usecase.PostRating;

import fontys.sem3.its.meem.business.exception.ExistingRatingException;
import fontys.sem3.its.meem.business.exception.InvalidIdentificationException;
import fontys.sem3.its.meem.business.exception.UnauthorizedDataAccessException;
import fontys.sem3.its.meem.domain.request.PostRating.CreatePostRatingRequest;
import fontys.sem3.its.meem.domain.response.PostRating.CreatePostRatingResponse;

import javax.validation.constraints.NotNull;

public interface CreatePostRatingUseCase {
    /**
     * @param request
     * @return FrontendPostRatings - a combined count of upvotes and downvotes
     * @throws UnauthorizedDataAccessException
     * @throws InvalidIdentificationException
     * @throws ExistingRatingException
     * @should return a front end post ratings object_ when rating successfully created
     * @should throw InvalidIdentificationException_ when post validation fails
     * @should throw InvalidIdentificationException_ when user validation fails
     * @should throw UnauthorizedDataAccessException_ when user is banned
     * @should throw UnauthorizedDataAccessException_ when request user different from access token user
     * @should throw ExistingRatingException_ when a rating from user for post already exists
     */
    CreatePostRatingResponse createPostRating(@NotNull CreatePostRatingRequest request) throws UnauthorizedDataAccessException,
            InvalidIdentificationException, ExistingRatingException;
}
