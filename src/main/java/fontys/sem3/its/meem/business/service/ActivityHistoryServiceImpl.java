package fontys.sem3.its.meem.business.service;

import fontys.sem3.its.meem.business.service.converter.ActivityHistoryConverter;
import fontys.sem3.its.meem.business.usecase.AccessToken.AccessTokenAuthenticityUseCase;
import fontys.sem3.its.meem.business.usecase.ActivityHistory.CreateActivityHistoryUseCase;
import fontys.sem3.its.meem.business.usecase.ActivityHistory.GetActivitiesHistoryUseCase;
import fontys.sem3.its.meem.business.usecase.User.UserValidatorUseCase;
import fontys.sem3.its.meem.domain.model.AccessToken;
import fontys.sem3.its.meem.domain.model.ActivityTypeEnum;
import fontys.sem3.its.meem.domain.response.ActivityHistory.GetActivitiesHistoryResponse;
import fontys.sem3.its.meem.persistence.entity.ActivityHistoryEntity;
import fontys.sem3.its.meem.persistence.entity.CommentEntity;
import fontys.sem3.its.meem.persistence.entity.PostEntity;
import fontys.sem3.its.meem.persistence.entity.UserEntity;
import fontys.sem3.its.meem.persistence.repository.ActivityHistoryRepository;
import fontys.sem3.its.meem.persistence.repository.CommentRepository;
import fontys.sem3.its.meem.persistence.repository.PostRepository;
import fontys.sem3.its.meem.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.Instant;

@Service
@AllArgsConstructor
public class ActivityHistoryServiceImpl implements CreateActivityHistoryUseCase, GetActivitiesHistoryUseCase {

    private final UserValidatorUseCase userValidator;
    private final ActivityHistoryRepository activityHistoryRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final AccessTokenAuthenticityUseCase accessTokenAuthenticityUseCase;
    private AccessToken requestAccessToken;


    /**
     * @param primaryUserId who has performed the action
     * @param activityType  what kind of action
     * @param postId        which post was it performed on
     * @should create activity history record when user upvotes post
     * @should create activity history record when user downvotes post
     * @should create activity history record when user uploads post
     * @should create activity history record when user edits post
     * @should create activity history record when user deletes post
     */
    @Override
    public void createActivityHistoryForUserInteractionsWithPost(@NotNull UserEntity primaryUserId, @NotNull ActivityTypeEnum activityType, @NotNull PostEntity postId) {

        activityHistoryRepository.save(
                ActivityHistoryEntity.builder()
                        .activityType(activityType)
                        .primaryUser(primaryUserId)
                        .activityTimestamp(Timestamp.from(Instant.now()))
                        .post(postId)
                        .build()
        );

    }

    /**
     * @param primaryUserId who has performed the action
     * @param activityType  what kind of action
     * @param postId        which post was it performed on
     * @param commentId     the comment left on the post
     * @should create activity history record when user comments on post
     */
    @Override
    public void createActivityHistoryForUserInteractionsWithPost(UserEntity primaryUserId, ActivityTypeEnum activityType, PostEntity postId, CommentEntity commentId) {

        activityHistoryRepository.save(
                ActivityHistoryEntity.builder()
                        .activityType(activityType)
                        .primaryUser(primaryUserId)
                        .activityTimestamp(Timestamp.from(Instant.now()))
                        .post(postId)
                        .comment(commentId)
                        .build()
        );

    }

    /**
     * @param primaryUserId who has performed the action
     * @param activityType  what kind of action
     * @param commentId     which comment was it performed on
     * @should create activity history record when user upvotes comment
     * @should create activity history record when user downvotes comment
     * @should create activity history record when user edits comment
     * @should create activity history record when user deletes comment
     */
    @Override
    public void createActivityHistoryForUserInteractionsWithComment(UserEntity primaryUserId, ActivityTypeEnum activityType, CommentEntity commentId) {

        activityHistoryRepository.save(
                ActivityHistoryEntity.builder()
                        .activityType(activityType)
                        .primaryUser(primaryUserId)
                        .activityTimestamp(Timestamp.from(Instant.now()))
                        .comment(commentId)
                        .build()
        );

    }

    /**
     * @param primaryUserId   who has performed the action
     * @param activityType    what kind of action
     * @param commentId       the comment the user has replied with
     * @param parentCommentId the comment the user has replied to
     * @should create activity history record when user replies to comment
     */
    @Override
    public void createActivityHistoryForUserInteractionsWithComment(UserEntity primaryUserId, ActivityTypeEnum activityType, CommentEntity commentId, CommentEntity parentCommentId) {

        activityHistoryRepository.save(
                ActivityHistoryEntity.builder()
                        .activityType(activityType)
                        .primaryUser(primaryUserId)
                        .comment(commentId)
                        .parentComment(parentCommentId)
                        .activityTimestamp(Timestamp.from(Instant.now()))
                        .build()
        );

    }

    /**
     * @param primaryUserId   who has performed the action
     * @param activityType    what kind of action
     * @param secondaryUserId who was the action performed on
     * @should create activity history record when moderator bans user
     * @should create activity history record when user blocks another user
     */
    @Override
    public void createActivityHistoryForUserInteractionsWithAnotherUser(UserEntity primaryUserId, ActivityTypeEnum activityType, UserEntity secondaryUserId) {

        activityHistoryRepository.save(
                ActivityHistoryEntity.builder()
                        .activityType(activityType)
                        .primaryUser(primaryUserId)
                        .secondaryUser(secondaryUserId)
                        .activityTimestamp(Timestamp.from(Instant.now()))
                        .build()
        );

    }

    /**
     * @param userId
     * @return GetActivitiesHistoryResponse
     * @should return GetActivitiesHistoryResponse for user when all checks pass
     */
    @Override
    public GetActivitiesHistoryResponse getActivitiesByUserId(int userId) {

        userValidator.validateId(userId);

        UserEntity user = userRepository.findByUserIdAndDeleted(userId, false);

        accessTokenAuthenticityUseCase.checkIfUserIdMatches(requestAccessToken, user);

        return GetActivitiesHistoryResponse.builder()
                .activityHistoryList(
                        activityHistoryRepository
                                .findAllByPrimaryUser(
                                        userRepository.findByUserIdAndDeleted(userId, false))
                                .stream()
                                .map(ActivityHistoryConverter::convert)
                                .toList()
                )
                .build();
    }

    /**
     * @param userId
     * @param activityType
     * @return GetActivitiesHistoryResponse
     * @should return GetActivitiesHistoryResponse for user and type when all checks pass
     */
    @Override
    public GetActivitiesHistoryResponse getActivitiesByUserIdAndType(int userId, @NotNull ActivityTypeEnum activityType) {

        userValidator.validateId(userId);

        UserEntity user = userRepository.findByUserIdAndDeleted(userId, false);

        accessTokenAuthenticityUseCase.checkIfUserIdMatches(requestAccessToken, user);

        return GetActivitiesHistoryResponse.builder()
                .activityHistoryList(
                        activityHistoryRepository
                                .findAllByActivityTypeAndPrimaryUser(
                                        activityType,
                                        userRepository.findByUserIdAndDeleted(userId, false))
                                .stream()
                                .map(ActivityHistoryConverter::convert)
                                .toList())
                .build();
    }
}
