package fontys.sem3.its.meem.business.usecase.CommentRating;

import fontys.sem3.its.meem.domain.model.FrontendCommentRatings;

public interface DeleteCommentRatingUseCase {
    FrontendCommentRatings deleteCommentRating(int commentId, int userId);
}
