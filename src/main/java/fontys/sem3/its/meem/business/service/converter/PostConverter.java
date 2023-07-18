package fontys.sem3.its.meem.business.service.converter;

import fontys.sem3.its.meem.domain.model.Post;
import fontys.sem3.its.meem.persistence.entity.PostEntity;

public class PostConverter {
    public static Post convert(PostEntity postEntity) {
        return Post.builder()
                .postId(postEntity.getPostId())
                .groupId(postEntity.getGroup().getGroupId())
                .postMedia(postEntity.getPostMedia().getMediaId())
                .postTitle(postEntity.getPostTitle())
                .datePosted(postEntity.getDatePosted())
                .categoryId(postEntity.getCategory().getCategoryId())
                .postAuthor(postEntity.getPostAuthor().getUserId())
                .postEdited(postEntity.getPostEdited())
                .postUrl(postEntity.getPostUrl())
                .deleted(postEntity.getDeleted())
                .explicit(postEntity.getExplicit())
                .build();
    }
}
