package fontys.sem3.its.meem.business.usecase.CommentRating;

import fontys.sem3.its.meem.domain.model.FrontendCommentRatings;
import fontys.sem3.its.meem.domain.request.CommentRating.CreateCommentRatingRequest;

import javax.validation.constraints.NotNull;

public interface CreateCommentRatingUseCase {
    FrontendCommentRatings createCommentRating(@NotNull CreateCommentRatingRequest request);
}
