package fontys.sem3.its.meem.business.usecase.PostRating;

import fontys.sem3.its.meem.business.exception.InvalidIdentificationException;
import fontys.sem3.its.meem.business.exception.NonExistingRatingException;
import fontys.sem3.its.meem.domain.response.PostRating.GetPostRatingResponse;

import javax.validation.constraints.NotBlank;

public interface GetPostRatingUseCase {
    //    GetPostRatingResponse getPostRating(int postId, int userId);

    /**
     * @param postUrl
     * @param userId
     * @return GetPostRatingResponse
     * @should return a GetPostRatingResponse object_ when checks pass
     * @should throw InvalidIdentificationException_ when post validation fails
     * @should throw InvalidIdentificationException_ when user validation fails
     * @should throw NonExistingRatingException_ when post rating validation fails
     */
    GetPostRatingResponse getPostRatingFromUserByPostUrl(@NotBlank String postUrl, int userId) throws InvalidIdentificationException, NonExistingRatingException;
}
