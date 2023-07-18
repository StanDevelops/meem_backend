package fontys.sem3.its.meem.business.service.converter;

import fontys.sem3.its.meem.domain.model.PostRating;
import fontys.sem3.its.meem.persistence.entity.PostRatingEntity;

public class PostRatingConverter {
    public static PostRating convert(PostRatingEntity postRatingEntity) {
        return PostRating.builder()
                .ratingId(postRatingEntity.getRatingId())
                .postId(postRatingEntity.getPostId().getPostId())
                .userId(postRatingEntity.getUserId().getUserId())
                .weight(postRatingEntity.getWeight())
                .build();
    }

}
