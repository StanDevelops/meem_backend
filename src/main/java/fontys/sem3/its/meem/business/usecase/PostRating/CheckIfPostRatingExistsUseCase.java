package fontys.sem3.its.meem.business.usecase.PostRating;

public interface CheckIfPostRatingExistsUseCase {
    /**
     * @param userId
     * @param postId
     * @param ratingWeight
     * @return boolean
     * @should return true_ when post rating with given ids and weight exists
     * @should return false_ when post rating with given ids and weight does not exist
     */
    Boolean postRatingExists(int userId, int postId, int ratingWeight);

    /**
     * @param userId
     * @param postId
     * @return boolean
     * @should return true_ when post rating with given ids exists
     * @should return false_ when post rating with given ids exist
     */
    Boolean postRatingExists(int userId, int postId);
}
