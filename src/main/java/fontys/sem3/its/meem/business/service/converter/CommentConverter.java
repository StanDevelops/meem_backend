package fontys.sem3.its.meem.business.service.converter;

import fontys.sem3.its.meem.domain.model.Comment;
import fontys.sem3.its.meem.persistence.entity.CommentEntity;

public class CommentConverter {
    public static Comment convert(CommentEntity commentEntity) {
        return Comment.builder()
                .commentId(commentEntity.getCommentId())
                .postId(commentEntity.getPost().getPostId())
                .commentAuthor(commentEntity.getCommentAuthor().getUserId())
                .dateCommented(commentEntity.getDateCommented())
                .commentBody(commentEntity.getCommentBody())
                .commentEdited(commentEntity.getCommentEdited())
                .parentCommentId(commentEntity.getParentCommentId())
                .depth(commentEntity.getDepth())
                .explicit(commentEntity.getExplicit())
                .deleted(commentEntity.getDeleted())
                .build();
    }

    //done
}
