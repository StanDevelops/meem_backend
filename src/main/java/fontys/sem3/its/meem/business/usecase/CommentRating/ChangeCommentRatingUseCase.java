package fontys.sem3.its.meem.business.usecase.CommentRating;

import fontys.sem3.its.meem.domain.model.FrontendCommentRatings;
import fontys.sem3.its.meem.domain.request.CommentRating.ChangeCommentRatingRequest;

import javax.validation.constraints.NotNull;

public interface ChangeCommentRatingUseCase {
    FrontendCommentRatings changeCommentRating(@NotNull ChangeCommentRatingRequest request);
}
