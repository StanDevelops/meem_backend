package fontys.sem3.its.meem.business.service;

import fontys.sem3.its.meem.business.exception.*;
import fontys.sem3.its.meem.business.service.converter.PostConverter;
import fontys.sem3.its.meem.business.service.converter.PostRatingConverter;
import fontys.sem3.its.meem.business.usecase.AccessToken.AccessTokenAuthenticityUseCase;
import fontys.sem3.its.meem.business.usecase.ActivityHistory.CreateActivityHistoryUseCase;
import fontys.sem3.its.meem.business.usecase.Category.CategoryValidatorUseCase;
import fontys.sem3.its.meem.business.usecase.Post.DeterminePostSortingGroupUseCase;
import fontys.sem3.its.meem.business.usecase.Post.PostValidatorUseCase;
import fontys.sem3.its.meem.business.usecase.PostRating.*;
import fontys.sem3.its.meem.business.usecase.SortingGroup.SortingGroupValidatorUseCase;
import fontys.sem3.its.meem.business.usecase.User.CalculateUserRatingUseCase;
import fontys.sem3.its.meem.business.usecase.User.UserValidatorUseCase;
import fontys.sem3.its.meem.domain.model.*;
import fontys.sem3.its.meem.domain.request.PostRating.ChangePostRatingRequest;
import fontys.sem3.its.meem.domain.request.PostRating.CreatePostRatingRequest;
import fontys.sem3.its.meem.domain.response.PostRating.AverageGroupRatingResponse;
import fontys.sem3.its.meem.domain.response.PostRating.CreatePostRatingResponse;
import fontys.sem3.its.meem.domain.response.PostRating.GetPostRatingResponse;
import fontys.sem3.its.meem.domain.response.PostRating.GetPostRatingsResponse;
import fontys.sem3.its.meem.persistence.entity.PostEntity;
import fontys.sem3.its.meem.persistence.entity.PostRatingEntity;
import fontys.sem3.its.meem.persistence.entity.SortingGroupEntity;
import fontys.sem3.its.meem.persistence.entity.UserEntity;
import fontys.sem3.its.meem.persistence.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PostRatingServiceImpl implements ChangePostRatingUseCase, CreatePostRatingUseCase, DeletePostRatingUseCase, GetPostRatingUseCase, GetPostRatingsUseCase, CheckIfPostRatingExistsUseCase, GetAveragePostRatingPerGroupUseCase {
    private final PostRatingRepository postRatingRepository;
    private final CategoryRepository categoryRepository;
    private final SortingGroupRepository sortingGroupRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostRatingValidatorUseCase postRatingValidator;
    private final PostValidatorUseCase postValidator;
    private final UserValidatorUseCase userValidator;
    private final SortingGroupValidatorUseCase sortingGroupValidator;
    private final CategoryValidatorUseCase categoryValidator;
    private final CalculateUserRatingUseCase calculateUserRatingUseCase;
    private final DeterminePostSortingGroupUseCase determinePostSortingGroupUseCase;
    private final CreateActivityHistoryUseCase createActivityHistoryUseCase;
    private final AccessTokenAuthenticityUseCase accessTokenAuthenticityUseCase;
    private AccessToken requestAccessToken;

    /**
     * @param request
     * @return FrontendPostRatings - a combined count of upvotes and downvotes
     * @should throw InvalidIdentificationException_ when post validation fails
     * @should throw InvalidIdentificationException_ when user validation fails
     * @should throw UnauthorizedDataAccessException_ when user is banned
     * @should throw UnauthorizedDataAccessException_ when request user different from access token user
     * @should throw NonExistingRatingException _when rating with given id does not exist
     * @should throw RedundantChangeException _when rating with given id and weight exists
     */
    @Transactional
    @Override
    public FrontendPostRatings changePostRating(@NotNull ChangePostRatingRequest request) throws UnauthorizedDataAccessException,
            InvalidIdentificationException, NonExistingRatingException, RedundantChangeException {

        userValidator.validateId(request.getUserId());

        accessTokenAuthenticityUseCase.checkIfUserIdMatches(requestAccessToken, userRepository.findByUserIdAndDeleted(request.getUserId(), false));

        accessTokenAuthenticityUseCase.checkIfBanned(requestAccessToken);

        postRatingValidator.validateId(request.getRatingId());

        if (postRatingExists(request.getUserId(), request.getPostId(), request.getNewRatingWeight())) {
            throw new RedundantChangeException();
        }

        PostEntity post = postRepository.findByPostIdAndDeleted(request.getPostId(), false);

        UserEntity user = userRepository.findByUserIdAndDeleted(request.getUserId(), false);

        postRatingRepository.changePostRating(request.getNewRatingWeight(), request.getRatingId());

        determinePostSortingGroupUseCase.determinePostGroup(request.getPostId());

        int postAuthorId = post.getPostAuthor().getUserId();

        calculateUserRatingUseCase.calculateUserRating(postAuthorId);

        if (request.getNewRatingWeight() > 0) {
            createActivityHistoryUseCase.createActivityHistoryForUserInteractionsWithPost(
                    user,
                    ActivityTypeEnum.UPVOTED,
                    post
            );
        }

        if (request.getNewRatingWeight() < 0) {
            createActivityHistoryUseCase.createActivityHistoryForUserInteractionsWithPost(
                    user,
                    ActivityTypeEnum.DOWNVOTED,
                    post
            );
        }

        return FrontendPostRatings.builder()
                .postId(request.getPostId())
                .upvoteCount(postRatingRepository.getCountOfPostRatingsByPostIdAndWeight(request.getPostId(), 1))
                .downvoteCount(postRatingRepository.getCountOfPostRatingsByPostIdAndWeight(request.getPostId(), -1))
                .build();
    }

    /**
     * @param request
     * @return FrontendPostRatings - a combined count of upvotes and downvotes
     * @throws UnauthorizedDataAccessException
     * @throws InvalidIdentificationException
     * @throws ExistingRatingException
     * @should return a front end post ratings object_ when rating successfully created
     * @should throw InvalidIdentificationException_ when post validation fails
     * @should throw InvalidIdentificationException_ when user validation fails
     * @should throw UnauthorizedDataAccessException_ when user is banned
     * @should throw UnauthorizedDataAccessException_ when request user different from access token user
     * @should throw ExistingRatingException_ when a rating from user for post already exists
     */
    @Override
    public CreatePostRatingResponse createPostRating(@NotNull CreatePostRatingRequest request) {

        userValidator.validateId(request.getUserId());

        postValidator.validateId(request.getPostId());

        accessTokenAuthenticityUseCase.checkIfBanned(requestAccessToken);

        accessTokenAuthenticityUseCase.checkIfUserIdMatches(requestAccessToken, userRepository.findByUserIdAndDeleted(request.getUserId(), false));

        PostEntity post = postRepository.findByPostIdAndDeleted(request.getPostId(), false);
        UserEntity user = userRepository.findByUserIdAndDeleted(request.getUserId(), false);

        if (Boolean.TRUE.equals(postRatingExists(request.getUserId(), request.getPostId(), request.getRatingWeight()))) {
            throw new ExistingRatingException();
        }

        if (request.getRatingWeight() > 0) {
            createActivityHistoryUseCase.createActivityHistoryForUserInteractionsWithPost(
                    user,
                    ActivityTypeEnum.UPVOTED,
                    post
            );
        }

        if (request.getRatingWeight() < 0) {
            createActivityHistoryUseCase.createActivityHistoryForUserInteractionsWithPost(
                    user,
                    ActivityTypeEnum.DOWNVOTED,
                    post
            );
        }

        PostRatingEntity tempPostRating = new PostRatingEntity().builder()
                .weight(request.getRatingWeight())
                .postId(post)
                .userId(user)
                .build();

        int ratingId = postRatingRepository.save(tempPostRating).getRatingId();

        determinePostSortingGroupUseCase.determinePostGroup(request.getPostId());

        int postAuthorId = post.getPostAuthor().getUserId();

        calculateUserRatingUseCase.calculateUserRating(postAuthorId);

        return CreatePostRatingResponse.builder()
                .postRatings(
                        FrontendPostRatings.builder()
                                .postId(request.getPostId())
                                .upvoteCount(postRatingRepository.getCountOfPostRatingsByPostIdAndWeight(request.getPostId(), 1))
                                .downvoteCount(postRatingRepository.getCountOfPostRatingsByPostIdAndWeight(request.getPostId(), -1))
                                .build())
                .ratingId(ratingId)
                .build();
    }

    /**
     * @param postId
     * @param userId
     * @return FrontendPostRatings - a combined count of upvotes and downvotes
     * @throws InvalidIdentificationException
     * @throws NonExistingRatingException
     * @throws UnauthorizedDataAccessException
     * @should delete post rating and return the updated count of the post ratings_ when checks pass
     * @should throw InvalidIdentificationException_ when user validation fails
     * @should throw InvalidIdentificationException_ when post validation fails
     * @should throw NonExistingRatingException _when rating with given ids does not exist
     * @should throw UnauthorizedDataAccessException_ when request user different from access token user
     */
    @Transactional
    @Override
    public FrontendPostRatings deletePostRating(@NotNull int ratingId, @NotNull int postId, @NotNull int userId) throws InvalidIdentificationException,
            NonExistingRatingException, UnauthorizedDataAccessException {

        userValidator.validateId(userId);

        accessTokenAuthenticityUseCase.checkIfUserIdMatches(requestAccessToken, userRepository.findByUserIdAndDeleted(userId, false));

        postValidator.validateId(postId);

        postRatingValidator.validateId(ratingId);

        PostEntity post = postRepository.findByPostIdAndDeleted(postId, false);
        UserEntity user = userRepository.findByUserIdAndDeleted(userId, false);

        postRatingRepository.deletePostRating(ratingId);
//        postRatingRepository.deleteByPostIdAndUserId(
//                postRepository.findByPostIdAndDeleted(postId, false),
//                userRepository.findByUserIdAndDeleted(userId, false)
//        );

        determinePostSortingGroupUseCase.determinePostGroup(postId);

        int postAuthorId = post.getPostAuthor().getUserId();

        calculateUserRatingUseCase.calculateUserRating(postAuthorId);

        return FrontendPostRatings.builder()
                .postId(postId)
                .upvoteCount(postRatingRepository.getCountOfPostRatingsByPostIdAndWeight(postId, 1))
                .downvoteCount(postRatingRepository.getCountOfPostRatingsByPostIdAndWeight(postId, -1))
                .build();
    }

    /**
     * @param userId
     * @param groupId
     * @return GetPostRatingsResponse
     * @should return a response with populated ratings list_ when checks pass and ratings exist
     * @should return a response with empty ratings list_ when checks pass and ratings do not exist
     * @should throw InvalidIdentificationException_ when sorting group validation fails
     * @should throw InvalidIdentificationException_ when user validation fails
     */
    @Override
    public GetPostRatingsResponse getPostRatingsFromUserBySortingGroup(int userId, int groupId) throws InvalidIdentificationException {

        sortingGroupValidator.validateId(groupId);

        userValidator.validateId(userId);

        accessTokenAuthenticityUseCase.checkIfUserIdMatches(requestAccessToken, userRepository.findByUserIdAndDeleted(userId, false));

        List<PostRating> returnedPostRatings = postRatingRepository.getPostRatingsFromUserBySortingGroup(userId, groupId).stream().map(PostRatingConverter::convert).toList();

        List<PostRating> noRatings = new ArrayList<>();

        if (returnedPostRatings.isEmpty()) {
            return GetPostRatingsResponse.builder().postRatings(noRatings).build();
        } else {
            return GetPostRatingsResponse.builder().postRatings(returnedPostRatings).build();
        }
    }

    /**
     * @param userId
     * @param groupId
     * @param categoryId
     * @return GetPostRatingsResponse
     * @should return a response with populated ratings list_ when checks pass and ratings exist
     * @should return a response with empty ratings list_ when checks pass and ratings do not exist
     * @should throw InvalidIdentificationException_ when sorting group validation fails
     * @should throw InvalidIdentificationException_ when user validation fails
     * @should throw InvalidIdentificationException_ when category validation fails
     */
    @Override
    public GetPostRatingsResponse getPostRatingsFromUserBySortingGroupAndCategory(int userId, int groupId,
                                                                                  int categoryId) throws InvalidIdentificationException, UnauthorizedDataAccessException {

        sortingGroupValidator.validateId(groupId);

        categoryValidator.validateId(categoryId);

        userValidator.validateId(userId);

        accessTokenAuthenticityUseCase.checkIfUserIdMatches(requestAccessToken, userRepository.findByUserIdAndDeleted(userId, false));

        List<PostRating> returnedPostRatings = postRatingRepository.getPostRatingsFromUserByCategoryAndSortingGroup(userId, categoryId, groupId).stream().map(PostRatingConverter::convert).toList();

        List<PostRating> noRatings = new ArrayList<>();

        if (returnedPostRatings.isEmpty()) {
            return GetPostRatingsResponse.builder().postRatings(noRatings).build();
        } else {
            return GetPostRatingsResponse.builder().postRatings(returnedPostRatings).build();
        }
    }

    /**
     * @param postUrl
     * @param userId
     * @return GetPostRatingResponse
     * @should return a GetPostRatingResponse object_ when checks pass
     * @should throw InvalidIdentificationException_ when post validation fails
     * @should throw InvalidIdentificationException_ when user validation fails
     * @should throw NonExistingRatingException_ when post rating validation fails
     */
    @Override
    public GetPostRatingResponse getPostRatingFromUserByPostUrl(@NotBlank String postUrl, @NotNull int userId) throws InvalidIdentificationException, UnauthorizedDataAccessException {

        postValidator.validateUrl(postUrl);

        userValidator.validateId(userId);

        accessTokenAuthenticityUseCase.checkIfUserIdMatches(requestAccessToken, userRepository.findByUserIdAndDeleted(userId, false));

        PostEntity postEntity = postRepository.findByPostUrl(postUrl);
        UserEntity userEntity = userRepository.findByUserIdAndDeleted(userId, false);

        if (postRatingExists(userId, postEntity.getPostId())) {
            PostRatingEntity rating = postRatingRepository.findByPostIdAndUserId(postEntity, userEntity);

            return GetPostRatingResponse.builder().postRating(PostRatingConverter.convert(rating)).build();
        } else {
            throw new NonExistingRatingException();
        }

    }

    /**
     * @param groupId
     * @return List of FrontEndPostRatings
     * @throws InvalidIdentificationException
     * @should return a list populated with front end post ratings_ when checks pass
     * @should throw InvalidIdentificationException_ when sorting group validation fails
     */
    @Override
    public List<FrontendPostRatings> getCombinedRatingsCountForAllPostsInSortingGroup(int groupId) throws InvalidIdentificationException {

        sortingGroupValidator.validateId(groupId);

        if (postRepository.findAllByGroup(sortingGroupRepository.findByGroupId(groupId)).isEmpty()) {
            return new ArrayList<>();
        }

        List<Post> postsInSortingGroup = postRepository.findAllByGroup(sortingGroupRepository.findByGroupId(groupId))
                .stream()
                .map(PostConverter::convert)
                .toList();
        List<FrontendPostRatings> listOfCombinedPostRatings = new ArrayList<>();

        postsInSortingGroup.forEach(
                post -> {
                    int postId = post.getPostId();
                    int upvoteCount = postRatingRepository.getCountOfPostRatingsByPostIdAndWeight(postId, 1);
                    int downvoteCount = postRatingRepository.getCountOfPostRatingsByPostIdAndWeight(postId, -1);
                    listOfCombinedPostRatings.add(new FrontendPostRatings(postId, upvoteCount, downvoteCount)
                    );
                });

        return listOfCombinedPostRatings;
    }

    /**
     * @param groupId
     * @param categoryId
     * @return List of FrontEndPostRatings
     * @throws InvalidIdentificationException
     * @should return a list populated with front end post ratings_ when checks pass
     * @should throw InvalidIdentificationException_ when sorting group validation fails
     * @should throw InvalidIdentificationException_ when category validation fails
     */
    @Override
    public List<FrontendPostRatings> getCombinedRatingsCountForAllPostsInSortingGroupAndCategory(int groupId,
                                                                                                 int categoryId) throws InvalidIdentificationException {
        categoryValidator.validateId(categoryId);

        sortingGroupValidator.validateId(groupId);

        if (postRepository.findByGroupAndCategoryAndDeletedFalse(
                groupId,
                categoryId).isEmpty()) {
            return new ArrayList<>();
        }

        List<Post> postsInSortingGroupAndCategory = postRepository.findByGroupAndCategoryAndDeletedFalse(
                        groupId,
                        categoryId)
                .stream()
                .map(PostConverter::convert)
                .toList();

        List<FrontendPostRatings> listOfCombinedPostRatings = new ArrayList<>();

        postsInSortingGroupAndCategory.forEach(
                post -> listOfCombinedPostRatings.add(
                        FrontendPostRatings.builder()
                                .postId(post.getPostId())
                                .upvoteCount(postRatingRepository.getCountOfPostRatingsByPostIdAndWeight(post.getPostId(), 1))
                                .downvoteCount(postRatingRepository.getCountOfPostRatingsByPostIdAndWeight(post.getPostId(), -1))
                                .build()
                ));

        return listOfCombinedPostRatings;
    }

    /**
     * @param postUrl
     * @return FrontendPostRatings - a combined count of upvotes and downvotes
     * @throws InvalidIdentificationException
     * @should return a  front end post ratings object_ when checks pass
     * @should throw InvalidIdentificationException_ when post validation fails
     */
    @Override
    public FrontendPostRatings getCombinedRatingsCountForAPostByUrl(@NotBlank String postUrl) throws InvalidIdentificationException {

        postValidator.validateUrl(postUrl);

        PostEntity postEntity = postRepository.findByPostUrl(postUrl);

        Post post = PostConverter.convert(postEntity);

        return FrontendPostRatings.builder()
                .postId(post.getPostId())
                .upvoteCount(postRatingRepository.getCountOfPostRatingsByPostIdAndWeight(post.getPostId(), 1))
                .downvoteCount(postRatingRepository.getCountOfPostRatingsByPostIdAndWeight(post.getPostId(), -1))
                .build();
    }

    /**
     * @param userId
     * @param postId
     * @param ratingWeight
     * @return boolean
     * @should return true_ when post rating with given ids and weight exists
     * @should return false_ when post rating with given ids and weight does not exist
     */
    @Override
    public Boolean postRatingExists(int userId, int postId, int ratingWeight) {

        return postRatingRepository.existsByUserIdAndPostIdAndWeight(
                userRepository.findByUserIdAndDeleted(userId, false), postRepository.findByPostIdAndDeleted(postId, false), ratingWeight);
    }

    /**
     * @param userId
     * @param postId
     * @return boolean
     * @should return true_ when post rating with given ids exists
     * @should return false_ when post rating with given ids exist
     */
    @Override
    public Boolean postRatingExists(int userId, int postId) {
        return postRatingRepository.existsByPostIdAndUserId(
                postRepository.findByPostIdAndDeleted(postId, false), userRepository.findByUserIdAndDeleted(userId, false)
        );
    }

    /**
     * @return AverageGroupRatingResponse
     * @should return a response with stats_ when id validation successful
     * @should return a response with empty ratings list_ when checks pass and ratings do not exist
     */
    @Override
    public AverageGroupRatingResponse getAverageGroupRatingStats() {
        List<SortingGroupEntity> groups = sortingGroupRepository.findAll();
        List<AverageGroupRating> averageRatings = new ArrayList<>();
        groups.forEach(
                group -> averageRatings.add(
                        postRatingRepository.countByPostId_Group(group) > 0 ?

                                AverageGroupRating.builder()
                                        .averageRating(postRatingRepository.getAverageRatingForSortingGroup(group))
                                        .downvotesCount((int) postRatingRepository.countByPostId_GroupAndWeightLessThan(group, 0))
                                        .upvotesCount((int) postRatingRepository.countByPostId_GroupAndWeightGreaterThan(group, 0))
                                        .groupName(group.getGroupName())
                                        .groupId(group.getGroupId())
                                        .build() : AverageGroupRating.builder()
                                .averageRating(0)
                                .downvotesCount(0)
                                .upvotesCount(0)
                                .groupName(group.getGroupName())
                                .groupId(group.getGroupId())
                                .build()));

        return AverageGroupRatingResponse.builder().statistics(averageRatings).build();
    }
}
