package fontys.sem3.its.meem.business.service;

import fontys.sem3.its.meem.business.usecase.AccessToken.AccessTokenAuthenticityUseCase;
import fontys.sem3.its.meem.business.usecase.User.UserValidatorUseCase;
import fontys.sem3.its.meem.domain.model.AccessToken;
import fontys.sem3.its.meem.persistence.repository.ActivityHistoryRepository;
import fontys.sem3.its.meem.persistence.repository.CommentRepository;
import fontys.sem3.its.meem.persistence.repository.PostRepository;
import fontys.sem3.its.meem.persistence.repository.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ActivityHistoryServiceImplTest {
    @Mock
    private UserValidatorUseCase userValidatorUseCase;
    @Mock
    private AccessTokenAuthenticityUseCase accessTokenAuthenticityUseCase;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private ActivityHistoryRepository activityHistoryRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private AccessToken accessToken;
    @InjectMocks
    private ActivityHistoryServiceImpl activityHistoryService;


//    /**
//     * @verifies create activity history record when user upvotes post
//     * @see ActivityHistoryServiceImpl#createActivityHistoryForUserInteractionsWithPost(int, fontys.sem3.its.meem.domain.model.ActivityTypeEnum, int)
//     */
//    @Test
//    void createActivityHistoryForUserInteractionsWithPost_shouldCreateActivityHistoryRecordWhenUserUpvotesPost() {
//
//        // Arrange
//        ActivityTypeEnum activityType = ActivityTypeEnum.UPVOTED;
//        PostEntity post = PostEntity.builder().postId(101).build();
//        UserEntity user = UserEntity.builder().userId(10).build();
//        Timestamp timestamp = Timestamp.valueOf("2023-01-19 03:14:07");
//        ActivityHistoryEntity activityHistory = ActivityHistoryEntity.builder()
//                .activityType(activityType)
//                .primaryUser(user)
//                .post(post)
//                .activityTimestamp(timestamp)
//                .build();
//
//        // Act
//
//        activityHistoryService.createActivityHistoryForUserInteractionsWithPost(user, activityType, post);
//
//        // Assert
//
//        verify(activityHistoryRepository).save(activityHistory);
//    }
//
//    /**
//     * @verifies create activity history record when user downvotes post
//     * @see ActivityHistoryServiceImpl#createActivityHistoryForUserInteractionsWithPost(int, fontys.sem3.its.meem.domain.model.ActivityTypeEnum, int)
//     */
//    @Test
//    void createActivityHistoryForUserInteractionsWithPost_shouldCreateActivityHistoryRecordWhenUserDownvotesPost() {
//
//        // Arrange
//        ActivityTypeEnum activityType = ActivityTypeEnum.DOWNVOTED;
//        Timestamp timestamp = Timestamp.valueOf("2023-01-19 03:14:07");
//        PostEntity post = PostEntity.builder().postId(101).build();
//        UserEntity user = UserEntity.builder().userId(10).build();
//        ActivityHistoryEntity activityHistory = ActivityHistoryEntity.builder()
//                .activityType(activityType)
//                .primaryUser(user)
//                .post(post)
//                .activityTimestamp(timestamp)
//                .build();
//        ActivityHistoryEntity savedActivityHistory = activityHistory;
//
////        savedActivityHistory.setActivityId(10);
//
////        when(activityHistoryRepository.save(activityHistory)).thenReturn(savedActivityHistory);
//
//        // Act
//
//        activityHistoryService.createActivityHistoryForUserInteractionsWithPost(user, activityType, post);
//
//        // Assert
//
//        verify(activityHistoryRepository).save(activityHistory);
//    }
//
//    /**
//     * @verifies create activity history record when user uploads post
//     * @see ActivityHistoryServiceImpl#createActivityHistoryForUserInteractionsWithPost(int, fontys.sem3.its.meem.domain.model.ActivityTypeEnum, int)
//     */
//    @Test
//    void createActivityHistoryForUserInteractionsWithPost_shouldCreateActivityHistoryRecordWhenUserUploadsPost() {
//        // Arrange
//        ActivityTypeEnum activityType = ActivityTypeEnum.UPLOADED;
//        Timestamp timestamp = Timestamp.valueOf("2023-01-19 03:14:07");
//        PostEntity post = PostEntity.builder().postId(101).build();
//        UserEntity user = UserEntity.builder().userId(10).build();
//        ActivityHistoryEntity activityHistory = ActivityHistoryEntity.builder()
//                .activityType(activityType)
//                .primaryUser(user)
//                .post(post)
//                .activityTimestamp(timestamp)
//                .build();
//
//
//        // Act
//
//        activityHistoryService.createActivityHistoryForUserInteractionsWithPost(user, activityType, post);
//
//        // Assert
//
//        verify(activityHistoryRepository).save(activityHistory);
//    }
//
//    /**
//     * @verifies create activity history record when user edits post
//     * @see ActivityHistoryServiceImpl#createActivityHistoryForUserInteractionsWithPost(int, fontys.sem3.its.meem.domain.model.ActivityTypeEnum, int)
//     */
//    @Test
//    void createActivityHistoryForUserInteractionsWithPost_shouldCreateActivityHistoryRecordWhenUserEditsPost() {
//        // Arrange
//        ActivityTypeEnum activityType = ActivityTypeEnum.EDITED;
//        Timestamp timestamp = Timestamp.valueOf("2023-01-19 03:14:07");
//        PostEntity post = PostEntity.builder().postId(101).build();
//        UserEntity user = UserEntity.builder().userId(10).build();
//        ActivityHistoryEntity activityHistory = ActivityHistoryEntity.builder()
//                .activityType(activityType)
//                .primaryUser(user)
//                .post(post)
//                .activityTimestamp(timestamp)
//                .build();
//
//
//        // Act
//
//        activityHistoryService.createActivityHistoryForUserInteractionsWithPost(user, activityType, post);
//
//        // Assert
//
//
//        verify(activityHistoryRepository).save(activityHistory);
//    }
//
//    /**
//     * @verifies create activity history record when user deletes post
//     * @see ActivityHistoryServiceImpl#createActivityHistoryForUserInteractionsWithPost(int, fontys.sem3.its.meem.domain.model.ActivityTypeEnum, int)
//     */
//    @Test
//    void createActivityHistoryForUserInteractionsWithPost_shouldCreateActivityHistoryRecordWhenUserDeletesPost() {
//        // Arrange
//        ActivityTypeEnum activityType = ActivityTypeEnum.DELETED;
//        Timestamp timestamp = Timestamp.valueOf("2023-01-19 03:14:07");
//        PostEntity post = PostEntity.builder().postId(101).build();
//        UserEntity user = UserEntity.builder().userId(10).build();
//        ActivityHistoryEntity activityHistory = ActivityHistoryEntity.builder()
//                .activityType(activityType)
//                .primaryUser(user)
//                .activityTimestamp(timestamp)
//                .post(post)
//                .build();
//
//
//        // Act
//
//        activityHistoryService.createActivityHistoryForUserInteractionsWithPost(user, activityType, post);
//
//        // Assert
//
//        verify(activityHistoryRepository).save(activityHistory);
//    }
//
//    /**
//     * @verifies create activity history record when user comments on post
//     * @see ActivityHistoryServiceImpl#createActivityHistoryForUserInteractionsWithPost(int, ActivityTypeEnum, int, int)
//     */
//    @Test
//    void createActivityHistoryForUserInteractionsWithPost_shouldCreateActivityHistoryRecordWhenUserCommentsOnPost() {
//        // Arrange
//        ActivityTypeEnum activityType = ActivityTypeEnum.COMMENTED;
//        Timestamp timestamp = Timestamp.valueOf("2023-01-19 03:14:07");
//
//        PostEntity post = PostEntity.builder().postId(101).build();
//        UserEntity user = UserEntity.builder().userId(10).build();
//        CommentEntity comment = CommentEntity.builder().commentId(11).build();
//        ActivityHistoryEntity activityHistory = ActivityHistoryEntity.builder()
//                .activityType(activityType)
//                .primaryUser(user)
//                .post(post)
//                .comment(comment)
//                .activityTimestamp(timestamp)
//                .build();
//
//        // Act
//
//        activityHistoryService.createActivityHistoryForUserInteractionsWithPost(user, activityType, post, comment);
//
//        // Assert
//
//        verify(activityHistoryRepository).save(activityHistory);
//    }
//
//    /**
//     * @verifies create activity history record when user upvotes comment
//     * @see ActivityHistoryServiceImpl#createActivityHistoryForUserInteractionsWithComment(int, fontys.sem3.its.meem.domain.model.ActivityTypeEnum, int)
//     */
//    @Test
//    void createActivityHistoryForUserInteractionsWithComment_shouldCreateActivityHistoryRecordWhenUserUpvotesComment() {
//
//        // Arrange
//        ActivityTypeEnum activityType = ActivityTypeEnum.UPVOTED;
//        CommentEntity comment = CommentEntity.builder().commentId(101).build();
//        Timestamp timestamp = Timestamp.valueOf("2023-01-19 03:14:07");
//        MediaEntity media = MediaEntity.builder()
//                .mediaId(1).mediaAddress("").mediaType(MediaTypeEnum.IMAGE_JPG)
//                .mediaSize(0).mediaName("").deleted(false).external(true).build();
//        UserEntity user = UserEntity.builder()
//                .userId(10)
//                .userRole(UserRoleEnum.REGULAR)
//                .username("")
//                .email("")
//                .password("")
//                .dateRegistered(timestamp)
//                .gender('M')
//                .userRating(0)
//                .honourableUser(false)
//                .isBanned(false)
//                .deleted(false)
//                .profilePicture(media)
//                .build();
//        ActivityHistoryEntity activityHistory = ActivityHistoryEntity.builder()
//                .activityType(activityType)
//                .primaryUser(user)
//                .comment(comment)
//                .activityTimestamp(timestamp)
//                .build();
//        // Act
//        activityHistoryService.createActivityHistoryForUserInteractionsWithComment(user, activityType, comment);
//
//        // Assert
//        verify(activityHistoryRepository).save(activityHistory);
//
//    }
//
//    /**
//     * @verifies create activity history record when user downvotes comment
//     * @see ActivityHistoryServiceImpl#createActivityHistoryForUserInteractionsWithComment(int, fontys.sem3.its.meem.domain.model.ActivityTypeEnum, int)
//     */
//    @Test
//    void createActivityHistoryForUserInteractionsWithComment_shouldCreateActivityHistoryRecordWhenUserDownvotesComment() {
//        // Arrange
//        ActivityTypeEnum activityType = ActivityTypeEnum.DOWNVOTED;
//        CommentEntity comment = CommentEntity.builder().commentId(101).build();
//        UserEntity user = UserEntity.builder().userId(10).build();
//        Timestamp timestamp = Timestamp.valueOf("2023-01-19 03:14:07");
//        ActivityHistoryEntity activityHistory = ActivityHistoryEntity.builder()
//                .activityType(activityType)
//                .primaryUser(user)
//                .comment(comment)
//                .activityTimestamp(timestamp)
//                .build();
//
//        // Act
//        activityHistoryService.createActivityHistoryForUserInteractionsWithComment(user, activityType, comment);
//
//        // Assert
//        verify(activityHistoryRepository).save(activityHistory);
//    }
//
//    /**
//     * @verifies create activity history record when user edits comment
//     * @see ActivityHistoryServiceImpl#createActivityHistoryForUserInteractionsWithComment(int, fontys.sem3.its.meem.domain.model.ActivityTypeEnum, int)
//     */
//    @Test
//    void createActivityHistoryForUserInteractionsWithComment_shouldCreateActivityHistoryRecordWhenUserEditsComment() {
//        // Arrange
//        ActivityTypeEnum activityType = ActivityTypeEnum.EDITED;
//        CommentEntity comment = CommentEntity.builder().commentId(101).build();
//        UserEntity user = UserEntity.builder().userId(10).build();
//        Timestamp timestamp = Timestamp.valueOf("2023-01-19 03:14:07");
//        ActivityHistoryEntity activityHistory = ActivityHistoryEntity.builder()
//                .activityType(activityType)
//                .primaryUser(user)
//                .comment(comment)
//                .activityTimestamp(timestamp)
//                .build();
//
//        // Act
//        activityHistoryService.createActivityHistoryForUserInteractionsWithComment(user, activityType, comment);
//
//        // Assert
//        verify(activityHistoryRepository).save(activityHistory);
//    }
//
//    /**
//     * @verifies create activity history record when user deletes comment
//     * @see ActivityHistoryServiceImpl#createActivityHistoryForUserInteractionsWithComment(int, ActivityTypeEnum, int)
//     */
//    @Test
//    void createActivityHistoryForUserInteractionsWithComment_shouldCreateActivityHistoryRecordWhenUserDeletesComment() throws Exception {
//        // Arrange
//        ActivityTypeEnum activityType = ActivityTypeEnum.DELETED;
//        CommentEntity comment = CommentEntity.builder().commentId(101).build();
//        UserEntity user = UserEntity.builder().userId(10).build();
//        Timestamp timestamp = Timestamp.valueOf("2023-01-19 03:14:07");
//        ActivityHistoryEntity activityHistory = ActivityHistoryEntity.builder()
//                .activityType(activityType)
//                .primaryUser(user)
//                .comment(comment)
//                .activityTimestamp(timestamp)
//                .build();
//
//        // Act
//        activityHistoryService.createActivityHistoryForUserInteractionsWithComment(user, activityType, comment);
//
//        // Assert
//        verify(activityHistoryRepository).save(activityHistory);
//
//    }
//
//    /**
//     * @verifies create activity history record when user replies to comment
//     * @see ActivityHistoryServiceImpl#createActivityHistoryForUserInteractionsWithComment(int, fontys.sem3.its.meem.domain.model.ActivityTypeEnum, int, int)
//     */
//    @Test
//    void createActivityHistoryForUserInteractionsWithComment_shouldCreateActivityHistoryRecordWhenUserRepliesToComment() {
//        // Arrange
//        ActivityTypeEnum activityType = ActivityTypeEnum.DELETED;
//        CommentEntity comment = CommentEntity.builder().commentId(101).build();
//        CommentEntity parentComment = CommentEntity.builder().commentId(100).build();
//        UserEntity user = UserEntity.builder().userId(10).build();
//        Timestamp timestamp = Timestamp.valueOf("2023-01-19 03:14:07");
//        ActivityHistoryEntity activityHistory = ActivityHistoryEntity.builder()
//                .activityType(activityType)
//                .primaryUser(user)
//                .comment(comment)
//                .parentComment(parentComment)
//                .activityTimestamp(timestamp)
//                .build();
//
//        // Act
//        activityHistoryService.createActivityHistoryForUserInteractionsWithComment(user, activityType, comment, parentComment);
//
//        // Assert
//        verify(activityHistoryRepository).save(activityHistory);
//    }
//
//
//    /**
//     * @verifies return GetActivitiesHistoryResponse for user when all checks pass
//     * @see ActivityHistoryServiceImpl#getActivitiesByUserId(int)
//     */
//    @Test
//    void getActivitiesByUserId_shouldReturnGetActivitiesHistoryResponseForUserWhenAllChecksPass() {
//        //TODO auto-generated
//        Assertions.fail("Not yet implemented");
//    }
//
//    /**
//     * @verifies return GetActivitiesHistoryResponse for user and type when all checks pass
//     * @see ActivityHistoryServiceImpl#getActivitiesByUserIdAndType(int, fontys.sem3.its.meem.domain.model.ActivityTypeEnum)
//     */
//    @Test
//    void getActivitiesByUserIdAndType_shouldReturnGetActivitiesHistoryResponseForUserAndTypeWhenAllChecksPass() {
//        //TODO auto-generated
//        Assertions.fail("Not yet implemented");
//    }
//
//    /**
//     * @verifies create activity history record when moderator bans user
//     * @see ActivityHistoryServiceImpl#createActivityHistoryForUserInteractionsWithAnotherUser(UserEntity, ActivityTypeEnum, UserEntity)
//     */
//    @Test
//    void createActivityHistoryForUserInteractionsWithAnotherUser_shouldCreateActivityHistoryRecordWhenModeratorBansUser() throws Exception {
//        //TODO auto-generated
//        Assertions.fail("Not yet implemented");
//    }
//
//    /**
//     * @verifies create activity history record when user blocks another user
//     * @see ActivityHistoryServiceImpl#createActivityHistoryForUserInteractionsWithAnotherUser(UserEntity, ActivityTypeEnum, UserEntity)
//     */
//    @Test
//    void createActivityHistoryForUserInteractionsWithAnotherUser_shouldCreateActivityHistoryRecordWhenUserBlocksAnotherUser() throws Exception {
//        //TODO auto-generated
//        Assertions.fail("Not yet implemented");
//    }
}


