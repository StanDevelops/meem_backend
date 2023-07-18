package fontys.sem3.its.meem.business.usecase.Comment;

import fontys.sem3.its.meem.domain.model.FrontendComment;
import fontys.sem3.its.meem.domain.request.Comment.CreateCommentRequest;

public interface CreateCommentUseCase {
    FrontendComment createComment(CreateCommentRequest request);
}
