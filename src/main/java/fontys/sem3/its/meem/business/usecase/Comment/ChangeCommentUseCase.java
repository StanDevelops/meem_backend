package fontys.sem3.its.meem.business.usecase.Comment;

import fontys.sem3.its.meem.domain.request.Comment.ChangeCommentRequest;

import javax.validation.constraints.NotNull;

public interface ChangeCommentUseCase {
    void changeComment(@NotNull ChangeCommentRequest request);
}
