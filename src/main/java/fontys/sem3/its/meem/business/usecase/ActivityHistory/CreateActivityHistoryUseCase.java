package fontys.sem3.its.meem.business.usecase.ActivityHistory;

import fontys.sem3.its.meem.domain.model.ActivityTypeEnum;
import fontys.sem3.its.meem.persistence.entity.CommentEntity;
import fontys.sem3.its.meem.persistence.entity.PostEntity;
import fontys.sem3.its.meem.persistence.entity.UserEntity;

import javax.validation.constraints.NotNull;

public interface CreateActivityHistoryUseCase {
    /**
     * @param primaryUserId who has performed the action
     * @param activityType  what kind of action
     * @param postId        which post was it performed on
     * @implNote This method's parameters create the following activities -
     * user has [UPVOTED / DOWNVOTED / UPLOADED / EDITED / DELETED] a post
     */
    void createActivityHistoryForUserInteractionsWithPost(@NotNull UserEntity primaryUserId, @NotNull ActivityTypeEnum activityType, PostEntity postId);

    /**
     * @param primaryUserId who has performed the action
     * @param activityType  what kind of action
     * @param postId        which post was it performed on
     * @param commentId     the comment left on the post
     * @implNote This method's parameters create the following activities -
     * user has [COMMENTED] on a post
     */
    void createActivityHistoryForUserInteractionsWithPost(@NotNull UserEntity primaryUserId, @NotNull ActivityTypeEnum activityType, PostEntity postId, CommentEntity commentId);

    /**
     * @param primaryUserId who has performed the action
     * @param activityType  what kind of action
     * @param commentId     which comment was it performed on
     * @implNote This method's parameters create the following activities -
     * user has [UPVOTED / DOWNVOTED / EDITED / DELETED] a comment
     */
    void createActivityHistoryForUserInteractionsWithComment(@NotNull UserEntity primaryUserId, @NotNull ActivityTypeEnum activityType, CommentEntity commentId);

    /**
     * @param primaryUserId   who has performed the action
     * @param activityType    what kind of action
     * @param commentId       the comment the user has replied with
     * @param parentCommentId the comment the user has replied to
     * @implNote This method's parameters create the following activities -
     * user has [REPLIED] to a comment
     */
    void createActivityHistoryForUserInteractionsWithComment(@NotNull UserEntity primaryUserId, @NotNull ActivityTypeEnum activityType, CommentEntity commentId, CommentEntity parentCommentId);

    /**
     * @param primaryUserId   who has performed the action
     * @param activityType    what kind of action
     * @param secondaryUserId who was the action performed on
     * @implNote This method's parameters create the following activities -
     * user has [BANNED / FOLLOWED / BLOCKED] another user
     */
    void createActivityHistoryForUserInteractionsWithAnotherUser(@NotNull UserEntity primaryUserId, @NotNull ActivityTypeEnum activityType, UserEntity secondaryUserId);
}

