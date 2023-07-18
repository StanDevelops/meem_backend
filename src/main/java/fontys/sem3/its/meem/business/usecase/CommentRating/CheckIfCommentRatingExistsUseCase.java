package fontys.sem3.its.meem.business.usecase.CommentRating;

public interface CheckIfCommentRatingExistsUseCase {
    boolean commentRatingExists(int userId, int commentId, int ratingWeight);
}
