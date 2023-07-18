package fontys.sem3.its.meem.business.usecase.PostRating;

import fontys.sem3.its.meem.domain.response.PostRating.AverageGroupRatingResponse;


public interface GetAveragePostRatingPerGroupUseCase {

    /**
     * @return AverageGroupRatingResponse
     * @should return a response with stats_ when id validation successful
     * @should return a response with empty ratings list_ when checks pass and ratings do not exist
     */
    AverageGroupRatingResponse getAverageGroupRatingStats();


}
