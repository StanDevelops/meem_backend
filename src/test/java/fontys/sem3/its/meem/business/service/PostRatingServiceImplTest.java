package fontys.sem3.its.meem.business.service;

import fontys.sem3.its.meem.business.usecase.AccessToken.AccessTokenAuthenticityUseCase;
import fontys.sem3.its.meem.business.usecase.ActivityHistory.CreateActivityHistoryUseCase;
import fontys.sem3.its.meem.business.usecase.Category.CategoryValidatorUseCase;
import fontys.sem3.its.meem.business.usecase.Post.DeterminePostSortingGroupUseCase;
import fontys.sem3.its.meem.business.usecase.Post.PostValidatorUseCase;
import fontys.sem3.its.meem.business.usecase.PostRating.PostRatingValidatorUseCase;
import fontys.sem3.its.meem.business.usecase.SortingGroup.SortingGroupValidatorUseCase;
import fontys.sem3.its.meem.business.usecase.User.CalculateUserRatingUseCase;
import fontys.sem3.its.meem.business.usecase.User.EvaluateUserHonourableStatusUseCase;
import fontys.sem3.its.meem.business.usecase.User.UserValidatorUseCase;
import fontys.sem3.its.meem.domain.model.AccessToken;
import fontys.sem3.its.meem.persistence.repository.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PostRatingServiceImplTest {
    @Mock
    private PostRatingRepository postRatingRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private SortingGroupRepository sortingGroupRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PostRatingValidatorUseCase postRatingValidator;
    @Mock
    private PostValidatorUseCase postValidator;
    @Mock
    private UserValidatorUseCase userValidator;
    @Mock
    private SortingGroupValidatorUseCase sortingGroupValidator;
    @Mock
    private CategoryValidatorUseCase categoryValidator;
    @Mock
    private CalculateUserRatingUseCase calculateUserRating;
    @Mock
    private EvaluateUserHonourableStatusUseCase evaluateUserHonourableStatus;
    @Mock
    private DeterminePostSortingGroupUseCase determinePostSortingGroupUseCase;
    @Mock
    private CreateActivityHistoryUseCase createActivityHistory;
    @Mock
    private AccessTokenAuthenticityUseCase accessTokenAuthenticity;
    @Mock
    private AccessToken requestAccessToken;
    @InjectMocks
    private PostRatingServiceImpl postRatingService;
//
//    /**
//     * @verifies throw InvalidIdentificationException_ when post validation fails
//     * @see PostRatingServiceImpl#changePostRating(fontys.sem3.its.meem.domain.request.PostRating.ChangePostRatingRequest)
//     */
//    @Test
//    void changePostRating_shouldThrowInvalidIdentificationException_WhenPostValidationFails() {
//        //arrange
//        ChangePostRatingRequest request = ChangePostRatingRequest.builder()
//                .userId(10)
//                .postId(101)
//                .newRatingWeight(1)
//                .build();
//
//        when(userValidator.validateId(request.getUserId())).thenReturn(true);
//
//        doThrow(InvalidIdentificationException.class).when(postValidator).validateId(request.getPostId());
//
//        //act + assert
//        assertThrows(InvalidIdentificationException.class, () -> postRatingService.changePostRating(request));
//
//        verify(userValidator).validateId(request.getUserId());
//        verify(postValidator).validateId(request.getPostId());
//    }
//
//    /**
//     * @verifies throw InvalidIdentificationException_ when user validation fails
//     * @see PostRatingServiceImpl#changePostRating(fontys.sem3.its.meem.domain.request.PostRating.ChangePostRatingRequest)
//     */
//    @Test
//    void changePostRating_shouldThrowInvalidIdentificationException_WhenUserValidationFails() {
//        //arrange
//        ChangePostRatingRequest request = ChangePostRatingRequest.builder()
//                .userId(10)
//                .postId(101)
//                .newRatingWeight(1)
//                .build();
//
//        doThrow(InvalidIdentificationException.class).when(userValidator).validateId(request.getUserId());
//
//        //act + assert
//        assertThrows(InvalidIdentificationException.class, () -> postRatingService.changePostRating(request));
//
//        verify(userValidator).validateId(request.getUserId());
//    }
//
//    /**
//     * @verifies throw UnauthorizedDataAccessException_ when user is banned
//     * @see PostRatingServiceImpl#changePostRating(fontys.sem3.its.meem.domain.request.PostRating.ChangePostRatingRequest)
//     */
//    @Test
//    void changePostRating_shouldThrowUnauthorizedDataAccessException_WhenUserIsBanned() {
//        //arrange
//        ChangePostRatingRequest request = ChangePostRatingRequest.builder()
//                .userId(10)
//                .postId(101)
//                .newRatingWeight(1)
//                .build();
//        UserEntity user = UserEntity.builder()
//                .userId(10)
//                .build();
////        when(userValidator.validateId(request.getUserId())).thenReturn(true);
////        doNothing().when(postValidator).validateId(request.getPostId());
//        when(userRepository.findByUserIdAndDeleted(request.getUserId(), false)).thenReturn(user);
////        when(accessTokenAuthenticity.checkIfUserIdMatches(requestAccessToken, user)).thenReturn(true);
//        doThrow(UnauthorizedDataAccessException.class).when(accessTokenAuthenticity).checkIfBanned(requestAccessToken);
//
//        //act + assert
//        assertThrows(UnauthorizedDataAccessException.class, () -> postRatingService.changePostRating(request));
//
//        verify(userValidator).validateId(request.getUserId());
//        verify(postValidator).validateId(request.getPostId());
//        verify(accessTokenAuthenticity).checkIfUserIdMatches(requestAccessToken, user);
//    }
//
//    /**
//     * @verifies throw UnauthorizedDataAccessException_ when request user different from access token user
//     * @see PostRatingServiceImpl#changePostRating(fontys.sem3.its.meem.domain.request.PostRating.ChangePostRatingRequest)
//     */
//    @Test
//    void changePostRating_shouldThrowUnauthorizedDataAccessException_WhenRequestUserDifferentFromAccessTokenUser() {
//        //arrange
//        ChangePostRatingRequest request = ChangePostRatingRequest.builder()
//                .userId(10)
//                .postId(101)
//                .newRatingWeight(1)
//                .build();
//        UserEntity user = UserEntity.builder()
//                .userId(10)
//                .build();
//        when(userRepository.findByUserIdAndDeleted(request.getUserId(), false)).thenReturn(user);
//        when(accessTokenAuthenticity.checkIfUserIdMatches(requestAccessToken, user)).thenThrow(UnauthorizedDataAccessException.class);
//
//        //act + assert
//        assertThrows(UnauthorizedDataAccessException.class, () -> postRatingService.changePostRating(request));
//
//        verify(userValidator).validateId(request.getUserId());
//        verify(postValidator).validateId(request.getPostId());
//        verify(accessTokenAuthenticity).checkIfUserIdMatches(requestAccessToken, user);
//    }
//
//    /**
//     * @verifies throw NonExistingRatingException _when rating with given id does not exist
//     * @see PostRatingServiceImpl#changePostRating(fontys.sem3.its.meem.domain.request.PostRating.ChangePostRatingRequest)
//     */
//    @Test
//    void changePostRating_shouldThrowNonExistingRatingException_whenRatingWithGivenIdDoesNotExist() {
//        //arrange
//        ChangePostRatingRequest request = ChangePostRatingRequest.builder()
//                .userId(10)
//                .postId(101)
//                .newRatingWeight(1)
//                .build();
//        UserEntity user = UserEntity.builder()
//                .userId(10)
//                .build();
//        when(userRepository.findByUserIdAndDeleted(request.getUserId(), false)).thenReturn(user);
//        when(postRatingValidator.validateId(request.getPostId(), request.getUserId())).thenThrow(NonExistingRatingException.class);
//
//        //act + assert
//        assertThrows(NonExistingRatingException.class, () -> postRatingService.changePostRating(request));
//
//        verify(userValidator).validateId(request.getUserId());
//        verify(postValidator).validateId(request.getPostId());
//        verify(accessTokenAuthenticity).checkIfUserIdMatches(requestAccessToken, user);
//        verify(accessTokenAuthenticity).checkIfBanned(requestAccessToken);
//    }
//
//    /**
//     * @verifies throw RedundantChangeException _when rating with given id and weight exists
//     * @see PostRatingServiceImpl#changePostRating(fontys.sem3.its.meem.domain.request.PostRating.ChangePostRatingRequest)
//     */
//    @Test
//    void changePostRating_shouldThrowRedundantChangeException_whenRatingWithGivenIdAndWeightExists() {
//        //arrange
//        ChangePostRatingRequest request = ChangePostRatingRequest.builder()
//                .userId(10)
//                .postId(101)
//                .newRatingWeight(1)
//                .build();
//        UserEntity user = UserEntity.builder()
//                .userId(10)
//                .build();
//        when(userRepository.findByUserIdAndDeleted(request.getUserId(), false)).thenReturn(user);
//        when(postRatingValidator.validateId(request.getPostId(), request.getUserId())).thenThrow(NonExistingRatingException.class);
//
//        //act + assert
//        assertThrows(NonExistingRatingException.class, () -> postRatingService.changePostRating(request));
//
//        verify(userValidator).validateId(request.getUserId());
//        verify(postValidator).validateId(request.getPostId());
//        verify(accessTokenAuthenticity).checkIfUserIdMatches(requestAccessToken, user);
//        verify(accessTokenAuthenticity).checkIfBanned(requestAccessToken);
//    }

}
