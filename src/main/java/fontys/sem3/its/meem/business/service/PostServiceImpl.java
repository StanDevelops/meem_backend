package fontys.sem3.its.meem.business.service;

import fontys.sem3.its.meem.business.exception.*;
import fontys.sem3.its.meem.business.service.converter.PostConverter;
import fontys.sem3.its.meem.business.usecase.AccessToken.AccessTokenAuthenticityUseCase;
import fontys.sem3.its.meem.business.usecase.ActivityHistory.CreateActivityHistoryUseCase;
import fontys.sem3.its.meem.business.usecase.Category.CategoryValidatorUseCase;
import fontys.sem3.its.meem.business.usecase.Media.CreateMediaUseCase;
import fontys.sem3.its.meem.business.usecase.Post.*;
import fontys.sem3.its.meem.business.usecase.SortingGroup.SortingGroupValidatorUseCase;
import fontys.sem3.its.meem.business.usecase.User.UserValidatorUseCase;
import fontys.sem3.its.meem.domain.model.AccessToken;
import fontys.sem3.its.meem.domain.model.ActivityTypeEnum;
import fontys.sem3.its.meem.domain.model.FrontendPost;
import fontys.sem3.its.meem.domain.model.Post;
import fontys.sem3.its.meem.domain.request.Post.ChangePostTitleRequest;
import fontys.sem3.its.meem.domain.request.Post.CreatePostRequest;
import fontys.sem3.its.meem.domain.response.Post.GetPostResponse;
import fontys.sem3.its.meem.domain.response.Post.GetPostsResponse;
import fontys.sem3.its.meem.persistence.entity.PostEntity;
import fontys.sem3.its.meem.persistence.entity.SortingGroupEntity;
import fontys.sem3.its.meem.persistence.entity.UserEntity;
import fontys.sem3.its.meem.persistence.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PostServiceImpl implements
        ChangePostTitleUseCase,
        CreatePostUseCase,
        DeletePostUseCase,
        GetPostsUseCase,
        GetPostUseCase,
        DeterminePostSortingGroupUseCase {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final SortingGroupRepository sortingGroupRepository;
    private final PostValidatorUseCase postValidator;
    private final CreateMediaUseCase createMediaUseCase;
    private final CategoryValidatorUseCase categoryValidator;
    private final SortingGroupValidatorUseCase groupValidator;
    private final MediaRepository mediaRepository;
    private final UserValidatorUseCase userValidator;
    private AccessToken requestAccessToken;
    private final PostRatingRepository postRatingRepository;
    private final CreateActivityHistoryUseCase createActivityHistoryUseCase;
    private final AccessTokenAuthenticityUseCase accessTokenAuthenticityUseCase;

    private String generatePostUrl() {
        return "m33m" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
    }

    private FrontendPost buildCombinedPost(Post post) {
        return FrontendPost.builder()
                .postId(post.getPostId())
                .postMedia(mediaRepository.findMediaEntityByMediaId(post.getPostMedia()).getMediaAddress())
                .postTitle(post.getPostTitle())
                .authorId(post.getPostAuthor())
                .authorName(userRepository.findByUserIdAndDeleted(post.getPostAuthor(), false).getUsername())
                .categoryId(post.getCategoryId())
                .groupId(post.getGroupId())
                .datePosted(post.getDatePosted())
                .pfpUrl(userRepository.findByUserIdAndDeleted(post.getPostAuthor(), false).getProfilePicture().getMediaAddress())
                .postEdited(post.getPostEdited())
                .categoryName(categoryRepository.findByCategoryId(post.getCategoryId()).getCategoryName())
                .postUrl(post.getPostUrl())
                .authorIsHonourable(userRepository.findByUserIdAndDeleted(post.getPostAuthor(), false).getHonourableUser())
                .explicit(post.getExplicit())
                .build();
    }

    private void checkIfBanned() {
        if (Objects.equals(requestAccessToken.getAuthority(), "BANNED")) {
            throw new UnauthorizedDataAccessException("USER_IS_BANNED");
        }
    }


    @Override
    public FrontendPost createPostWithExternalMedia(CreatePostRequest request) throws InvalidIdentificationException, UnauthorizedDataAccessException {

        userValidator.validateId(request.getAuthorId());

//        groupValidator.validateId(request.getGroupId());

        categoryValidator.validateId(request.getCategoryId());


        accessTokenAuthenticityUseCase.checkIfUserIdMatches(requestAccessToken, userRepository.findByUserIdAndDeleted(request.getAuthorId(), false));

        checkIfBanned();

        PostEntity tempPost = PostEntity.builder()
                .postAuthor(userRepository.findByUserIdAndDeleted(request.getAuthorId(), false))
                .postTitle(request.getPostTitle())
                .postMedia(createMediaUseCase.createMedia(null, null, request.getMediaAddress(), 0, true))
                .category(categoryRepository.findByCategoryId(request.getCategoryId()))
                .group(sortingGroupRepository.findByGroupRank(4))
                .deleted(false)
                .explicit(request.getExplicit())
                .postEdited(false)
                .postUrl(generatePostUrl())
                .datePosted(Timestamp.from(Instant.now()))
                .build();

        PostEntity savedPost = postRepository.save(tempPost);
        createActivityHistoryUseCase.createActivityHistoryForUserInteractionsWithPost(
                userRepository.findByUserIdAndDeleted(request.getAuthorId(), false),
                ActivityTypeEnum.UPLOADED,
                savedPost
        );

        return buildCombinedPost(PostConverter.convert(savedPost));
    }

    @Transactional
    @Override
    public void changeTitle(ChangePostTitleRequest request) {

        postValidator.validateId(request.getPostId());

        UserEntity postAuthor = postRepository.findByPostId(request.getPostId()).getPostAuthor();

        if (!Objects.equals(postAuthor.getUserRole().toString(), requestAccessToken.getAuthority())) {
            throw new InvalidAccessTokenException("AUTHORITY_OVERREACH_DETECTED");
        }

        checkIfBanned();

        accessTokenAuthenticityUseCase.checkIfUserIdMatches(requestAccessToken, postAuthor);

        postRepository.updatePostTitle(request.getNewTitle(), request.getPostId());

        createActivityHistoryUseCase.createActivityHistoryForUserInteractionsWithPost(
                postAuthor,
                ActivityTypeEnum.EDITED,
                postRepository.findByPostIdAndDeleted(request.getPostId(), false)
        );
    }

    @Transactional
    @Override
    public void deletePost(@NotNull int postId) {

        postValidator.validateId(postId);

        UserEntity postAuthor = postRepository.findByPostId(postId).getPostAuthor();

//        checkIfAuthoritiesMatch();

        checkIfBanned();


        if (!Objects.equals(requestAccessToken.getAuthority(), "MOD") || !Objects.equals(requestAccessToken.getAuthority(), "ADMIN")) {
            accessTokenAuthenticityUseCase.checkIfUserIdMatches(requestAccessToken, postAuthor);
        }

        postRepository.deletePostEntityByPostId(postId);

        createActivityHistoryUseCase.createActivityHistoryForUserInteractionsWithPost(
                postAuthor,
                ActivityTypeEnum.DELETED,
                postRepository.findByPostId(postId)
        );
    }

    @Override
    public GetPostResponse getPostById(int postId) {

        postValidator.validateId(postId);

        Post post = PostConverter.convert(postRepository.findByPostIdAndDeleted(postId, false));

        return GetPostResponse.builder().post(buildCombinedPost(post)).build();
    }

    @Override
    public GetPostResponse getPostByUrl(String postUrl) {

        if (Objects.equals(postUrl.replaceAll("\\s+", ""), "")) {
            throw new EmptyFieldException(); //empty field detected, throw a hissy.
        }

        postValidator.validateUrl(postUrl);

        PostEntity returnedPost = postRepository.findByPostUrl(postUrl);

        Post post = PostConverter.convert(returnedPost);

        return GetPostResponse.builder().post(buildCombinedPost(post)).build();
    }

//    @Override
//    public GetPostsResponse getPostsByCategory(int categoryId) {
//
//        categoryValidator.validateId(categoryId);
//
//        List<PostEntity> returnedPosts = postRepository.findAllByCategoryAndDeleted(categoryRepository.findByCategoryId(categoryId), false);
//
//        checkIfRepoIsEmpty(returnedPosts);
//
//        List<Post> convertedPosts = returnedPosts
//                .stream()
//                .map(PostConverter::convert)
//                .toList();
//
//        List<FrontendPost> combinedList = new ArrayList<>();
//        convertedPosts.forEach(post -> {
//            combinedList.add(buildCombinedPost(post));
//        });
//
//        return GetPostsResponse.builder()
//                .posts(combinedList)
//                .build();
//    }

    @Override
    public GetPostsResponse getPostsBySortingGroup(int groupId) {

        groupValidator.validateId(groupId);

        List<PostEntity> returnedPosts = postRepository.findByGroupAndDeletedOrderByDatePostedDesc(sortingGroupRepository.findByGroupId(groupId), false);

        checkIfRepoIsEmpty(returnedPosts);

        List<Post> convertedPosts = returnedPosts
                .stream()
                .map(PostConverter::convert)
                .toList();

        List<FrontendPost> combinedList = new ArrayList<>();

        convertedPosts.forEach(post -> {
            combinedList.add(buildCombinedPost(post));
        });

        return GetPostsResponse.builder()
                .posts(combinedList)
                .build();
    }

    @Override
    public GetPostsResponse getPostsBySortingGroupAndCategory(int groupId, int categoryId) {

        categoryValidator.validateId(categoryId);

        groupValidator.validateId(groupId);

        List<PostEntity> returnedPosts = postRepository.findByGroupAndCategoryAndDeletedFalse(groupId, categoryId);

        checkIfRepoIsEmpty(returnedPosts);

        List<Post> convertedPosts = returnedPosts
                .stream()
                .map(PostConverter::convert)
                .toList();

        List<FrontendPost> combinedList = new ArrayList<>();
        convertedPosts.forEach(post -> {
            combinedList.add(buildCombinedPost(post));
        });

        return GetPostsResponse.builder()
                .posts(combinedList)
                .build();
    }

//    @Override
//    public GetPostsResponse getPostsByAuthor(int authorId) {
//
//        userValidator.validateId(authorId);
//
//        List<PostEntity> returnedPosts = postRepository.findAllByPostAuthorAndDeleted(userRepository.findByUserIdAndDeleted(authorId, false), false);
//
//        checkIfRepoIsEmpty(returnedPosts);
//
//        List<Post> convertedPosts = returnedPosts
//                .stream()
//                .map(PostConverter::convert)
//                .toList();
//
//        List<FrontendPost> combinedList = new ArrayList<>();
//        convertedPosts.forEach(post -> {
//            combinedList.add(buildCombinedPost(post));
//        });
//
//        return GetPostsResponse.builder()
//                .posts(combinedList)
//                .build();
//    }

    private void checkIfRepoIsEmpty(List<PostEntity> returnedPosts) {
        if (returnedPosts.isEmpty()) {
            throw new EmptyRepositoryException();
        }
    }

    @Override
    public void determinePostGroup(int postId) {

        postValidator.validateId(postId);

        int ratingSum =
                (postRatingRepository.getCountOfPostRatingsByPostIdAndWeight(postId, -1) * -1)
                        +
                        postRatingRepository.getCountOfPostRatingsByPostIdAndWeight(postId, 1);

//        int maxRank = sortingGroupRepository.getMaxRank();

//        int minRank = sortingGroupRepository.getMinRank();

        int determinedRank;

//        PostEntity post = postRepository.findByPostId(postId);

        SortingGroupEntity determinedGroup;

//        SortingGroupEntity currentGroup = sortingGroupRepository.findByGroupId(post.getGroupId().getGroupId()).get();

//        List<SortingGroupEntity> allGroups = sortingGroupRepository.findAll();


        if (ratingSum >= 10 && ratingSum < 20) {
            determinedRank = 3;
            determinedGroup = sortingGroupRepository.findByGroupRank(determinedRank);
            postRepository.updatePostSortingGroup(determinedGroup.getGroupId(), postId);
        }
        if (ratingSum >= 20 && ratingSum < 40) {
            determinedRank = 2;
            determinedGroup = sortingGroupRepository.findByGroupRank(determinedRank);
            postRepository.updatePostSortingGroup(determinedGroup.getGroupId(), postId);
        }
        if (ratingSum >= 40) {
            determinedRank = 1;
            determinedGroup = sortingGroupRepository.findByGroupRank(determinedRank);
            postRepository.updatePostSortingGroup(determinedGroup.getGroupId(), postId);
        }
    }
}
