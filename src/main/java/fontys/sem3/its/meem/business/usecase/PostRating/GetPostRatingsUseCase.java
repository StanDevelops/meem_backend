package fontys.sem3.its.meem.business.usecase.PostRating;

import fontys.sem3.its.meem.business.exception.InvalidIdentificationException;
import fontys.sem3.its.meem.business.exception.UnauthorizedDataAccessException;
import fontys.sem3.its.meem.domain.model.FrontendPostRatings;
import fontys.sem3.its.meem.domain.response.PostRating.GetPostRatingsResponse;

import java.util.List;

public interface GetPostRatingsUseCase {
//    GetPostRatingsResponse getAllPostRatings();

//    GetPostRatingsResponse getPostRatingsById(int postId);

//    GetPostRatingsResponse getPostRatingsFromUser(int userId);

    /**
     * @param userId
     * @param groupId
     * @return GetPostRatingsResponse
     * @should return a response with populated ratings list_ when checks pass and ratings exist
     * @should return a response with empty ratings list_ when checks pass and ratings do not exist
     * @should throw InvalidIdentificationException_ when sorting group validation fails
     * @should throw InvalidIdentificationException_ when user validation fails
     */
    GetPostRatingsResponse getPostRatingsFromUserBySortingGroup(int userId, int groupId) throws InvalidIdentificationException, UnauthorizedDataAccessException;

    //    GetPostRatingsResponse getPostRatingsFromUserForPostByUrl(int userId, String postUrl);

    /**
     * @param userId
     * @param groupId
     * @param categoryId
     * @return GetPostRatingsResponse
     * @should return a response with populated ratings list_ when checks pass and ratings exist
     * @should return a response with empty ratings list_ when checks pass and ratings do not exist
     * @should throw InvalidIdentificationException_ when sorting group validation fails
     * @should throw InvalidIdentificationException_ when user validation fails
     * @should throw InvalidIdentificationException_ when category validation fails
     */
    GetPostRatingsResponse getPostRatingsFromUserBySortingGroupAndCategory(int userId, int groupId, int categoryId) throws InvalidIdentificationException, UnauthorizedDataAccessException;


//    GetPostRatingsResponse getPostRatingsBySortingGroup(int groupId);

//    GetPostRatingsResponse getPostRatingsByCategory(int categoryId);

    //   FrontendPostRatings getRatingsCountForPost(int postId);
//    List<FrontendPostRatings> getCombinedRatingsCountForAllPostsInCategory(int categoryId);

    /**
     * @param groupId
     * @return List of FrontEndPostRatings
     * @throws InvalidIdentificationException
     * @should return a list populated with front end post ratings_ when checks pass
     * @should throw InvalidIdentificationException_ when sorting group validation fails
     */
    List<FrontendPostRatings> getCombinedRatingsCountForAllPostsInSortingGroup(int groupId) throws InvalidIdentificationException;

    /**
     * @param groupId
     * @param categoryId
     * @return List of FrontEndPostRatings
     * @throws InvalidIdentificationException
     * @should return a list populated with front end post ratings_ when checks pass
     * @should throw InvalidIdentificationException_ when sorting group validation fails
     * @should throw InvalidIdentificationException_ when category validation fails
     */
    List<FrontendPostRatings> getCombinedRatingsCountForAllPostsInSortingGroupAndCategory(int groupId, int categoryId) throws InvalidIdentificationException;

    /**
     * @param postUrl
     * @return FrontendPostRatings - a combined count of upvotes and downvotes
     * @should return a  front end post ratings object_ when checks pass
     * @should throw InvalidIdentificationException_ when post validation fails
     */
    FrontendPostRatings getCombinedRatingsCountForAPostByUrl(String postUrl) throws InvalidIdentificationException;


//    int getSumOfPostRatingsByPost(int postId);

//    int getCountOfPostRatingsForPostByWeight(int postId, int ratingWeight);

//    int getSumOfPostRatingsBySortingGroup(int groupId);

//    int getSumOfPostRatingsBySortingGroupAndWeight(int groupId, int ratingWeight);

//    int getSumOfPostRatingsByCategory(int categoryId);

//    int getSumOfPostRatingsByCategoryAndWeight(int categoryId, int ratingWeight);
}
