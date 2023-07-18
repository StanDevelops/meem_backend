package fontys.sem3.its.meem.business.usecase.Comment;

import fontys.sem3.its.meem.domain.response.Comment.GetCommentsResponse;

public interface GetCommentsUseCase {
    GetCommentsResponse getCommentsByPostUrl(String postUrl);
}
