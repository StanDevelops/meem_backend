package fontys.sem3.its.meem.business.usecase.CommentRating;

import fontys.sem3.its.meem.domain.model.FrontendCommentRatings;
import fontys.sem3.its.meem.domain.response.CommentRating.GetCommentRatingsResponse;

import java.util.List;

public interface GetCommentRatingsUseCase {
    GetCommentRatingsResponse getCommentRatingsFromUserByPostUrl(int userId, String postUrl);

    List<FrontendCommentRatings> getCombinedRatingsCountForAllCommentsOnPost(String postUrl);
}
