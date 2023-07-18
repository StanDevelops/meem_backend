package fontys.sem3.its.meem.business.usecase.Comment;

import fontys.sem3.its.meem.domain.request.Comment.CreateCommentReplyRequest;

import javax.validation.constraints.NotNull;

public interface ReplyToCommentUseCase {
    void createCommentReply(@NotNull CreateCommentReplyRequest request);
}
