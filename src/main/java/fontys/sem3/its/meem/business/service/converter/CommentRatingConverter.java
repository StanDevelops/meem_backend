package fontys.sem3.its.meem.business.service.converter;

import fontys.sem3.its.meem.domain.model.CommentRating;
import fontys.sem3.its.meem.persistence.entity.CommentRatingEntity;

public class CommentRatingConverter {
    public static CommentRating convert(CommentRatingEntity commentRatingEntity) {
        return CommentRating.builder()
                .ratingId(commentRatingEntity.getRatingId())
                .commentId(commentRatingEntity.getCommentId().getCommentId())
                .userId(commentRatingEntity.getUserId().getUserId())
                .weight(commentRatingEntity.getWeight())
                .build();
    }

}
