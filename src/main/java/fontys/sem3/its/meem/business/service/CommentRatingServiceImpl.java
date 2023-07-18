package fontys.sem3.its.meem.business.service;

import fontys.sem3.its.meem.business.exception.RedundantChangeException;
import fontys.sem3.its.meem.business.exception.UnauthorizedDataAccessException;
import fontys.sem3.its.meem.business.service.converter.CommentRatingConverter;
import fontys.sem3.its.meem.business.usecase.AccessToken.AccessTokenAuthenticityUseCase;
import fontys.sem3.its.meem.business.usecase.ActivityHistory.CreateActivityHistoryUseCase;
import fontys.sem3.its.meem.business.usecase.Comment.CommentValidatorUseCase;
import fontys.sem3.its.meem.business.usecase.CommentRating.*;
import fontys.sem3.its.meem.business.usecase.Post.PostValidatorUseCase;
import fontys.sem3.its.meem.business.usecase.User.CalculateUserRatingUseCase;
import fontys.sem3.its.meem.business.usecase.User.EvaluateUserHonourableStatusUseCase;
import fontys.sem3.its.meem.business.usecase.User.UserValidatorUseCase;
import fontys.sem3.its.meem.domain.model.AccessToken;
import fontys.sem3.its.meem.domain.model.ActivityTypeEnum;
import fontys.sem3.its.meem.domain.model.FrontendCommentRatings;
import fontys.sem3.its.meem.domain.request.CommentRating.ChangeCommentRatingRequest;
import fontys.sem3.its.meem.domain.request.CommentRating.CreateCommentRatingRequest;
import fontys.sem3.its.meem.domain.response.CommentRating.GetCommentRatingsResponse;
import fontys.sem3.its.meem.persistence.entity.CommentEntity;
import fontys.sem3.its.meem.persistence.entity.CommentRatingEntity;
import fontys.sem3.its.meem.persistence.entity.PostEntity;
import fontys.sem3.its.meem.persistence.repository.CommentRatingRepository;
import fontys.sem3.its.meem.persistence.repository.CommentRepository;
import fontys.sem3.its.meem.persistence.repository.PostRepository;
import fontys.sem3.its.meem.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CommentRatingServiceImpl implements ChangeCommentRatingUseCase, CheckIfCommentRatingExistsUseCase, CreateCommentRatingUseCase, DeleteCommentRatingUseCase, GetCommentRatingsUseCase {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRatingValidatorUseCase commentRatingValidatorUseCase;
    private final CommentValidatorUseCase commentValidatorUseCase;
    private final UserValidatorUseCase userValidatorUseCase;
    private AccessToken requestAccessToken;
    private final CommentRatingRepository commentRatingRepository;
    private final CommentRepository commentRepository;
    private final CalculateUserRatingUseCase calculateUserRatingUseCase;
    private final EvaluateUserHonourableStatusUseCase evaluateUserHonourableStatusUseCase;
    private final CreateActivityHistoryUseCase createActivityHistoryUseCase;
    private final PostValidatorUseCase postValidator;
    private final AccessTokenAuthenticityUseCase accessTokenAuthenticityUseCase;


    private void checkIfBanned() {
        if (Objects.equals(requestAccessToken.getAuthority(), "BANNED")) {
            throw new UnauthorizedDataAccessException("USER_IS_BANNED");
        }
    }

    @Override
    public FrontendCommentRatings changeCommentRating(@NotNull ChangeCommentRatingRequest request) {

        userValidatorUseCase.validateId(request.getUserId());

        commentValidatorUseCase.validateId(request.getCommentId());

        accessTokenAuthenticityUseCase.checkIfUserIdMatches(requestAccessToken, userRepository.findByUserIdAndDeleted(request.getUserId(), false));

        checkIfBanned();

        if (commentRatingExists(request.getUserId(), request.getCommentId(), request.getNewRatingWeight())) {
            throw new RedundantChangeException();
        }

        commentRatingRepository.changeCommentRating(request.getNewRatingWeight(), request.getUserId(), request.getCommentId());

        int commentAuthorId = commentRepository.findByCommentAuthorAndDeleted(userRepository.findByUserIdAndDeleted(request.getUserId(), false), false).getCommentAuthor().getUserId();

        calculateUserRatingUseCase.calculateUserRating(commentAuthorId);

        evaluateUserHonourableStatusUseCase.evaluateUserHonourableStatus(commentAuthorId);

        if (request.getNewRatingWeight() > 0) {
            createActivityHistoryUseCase.createActivityHistoryForUserInteractionsWithComment(
                    userRepository.findByUserIdAndDeleted(request.getUserId(), false),
                    ActivityTypeEnum.UPVOTED,
                    commentRepository.findByCommentIdAndDeleted(request.getCommentId(), false)
            );
        }
        if (request.getNewRatingWeight() < 0) {
            createActivityHistoryUseCase.createActivityHistoryForUserInteractionsWithComment(
                    userRepository.findByUserIdAndDeleted(request.getUserId(), false),
                    ActivityTypeEnum.DOWNVOTED,
                    commentRepository.findByCommentIdAndDeleted(request.getCommentId(), false)
            );
        }

        return FrontendCommentRatings.builder()
                .commentId(request.getCommentId())
                .upvoteCount(commentRatingRepository.getCountOfCommentRatingsByCommentIdAndWeight(request.getCommentId(), 1))
                .downvoteCount(commentRatingRepository.getCountOfCommentRatingsByCommentIdAndWeight(request.getCommentId(), -1))
                .build();
    }


    @Override
    public boolean commentRatingExists(int userId, int commentId, int ratingWeight) {

        commentValidatorUseCase.validateId(commentId);

        userValidatorUseCase.validateId(userId);

        return commentRatingRepository.existsByCommentAndUserAndWeight(commentId, userId, ratingWeight);
    }


    @Override
    public FrontendCommentRatings createCommentRating(CreateCommentRatingRequest request) {

        userValidatorUseCase.validateId(request.getUserId());

        commentValidatorUseCase.validateId(request.getCommentId());

        accessTokenAuthenticityUseCase.checkIfUserIdMatches(requestAccessToken, userRepository.findByUserIdAndDeleted(request.getUserId(), false));

        checkIfBanned();

        if (commentRatingExists(request.getUserId(), request.getCommentId(), request.getRatingWeight())) {
            throw new RedundantChangeException();
        }

        commentRatingRepository.save(
                CommentRatingEntity.builder()
                        .commentId(commentRepository.findByCommentIdAndDeleted(request.getCommentId(), false))
                        .userId(userRepository.findByUserIdAndDeleted(request.getUserId(), false))
                        .weight(request.getRatingWeight())
                        .build()
        );

        int commentAuthorId = commentRepository.findByCommentAuthorAndDeleted(userRepository.findByUserIdAndDeleted(request.getUserId(), false), false).getCommentAuthor().getUserId();

        calculateUserRatingUseCase.calculateUserRating(commentAuthorId);

        evaluateUserHonourableStatusUseCase.evaluateUserHonourableStatus(commentAuthorId);

        if (request.getRatingWeight() > 0) {
            createActivityHistoryUseCase.createActivityHistoryForUserInteractionsWithComment(
                    userRepository.findByUserIdAndDeleted(request.getUserId(), false),
                    ActivityTypeEnum.UPVOTED,
                    commentRepository.findByCommentIdAndDeleted(request.getCommentId(), false)
            );
        }
        if (request.getRatingWeight() < 0) {
            createActivityHistoryUseCase.createActivityHistoryForUserInteractionsWithComment(
                    userRepository.findByUserIdAndDeleted(request.getUserId(), false),
                    ActivityTypeEnum.DOWNVOTED,
                    commentRepository.findByCommentIdAndDeleted(request.getCommentId(), false)
            );
        }

        return FrontendCommentRatings.builder()
                .commentId(request.getCommentId())
                .upvoteCount(commentRatingRepository.getCountOfCommentRatingsByCommentIdAndWeight(request.getCommentId(), 1))
                .downvoteCount(commentRatingRepository.getCountOfCommentRatingsByCommentIdAndWeight(request.getCommentId(), -1))
                .build();
    }

    /**
     * @param commentId
     * @param userId
     * @return
     */
    @Override
    public FrontendCommentRatings deleteCommentRating(int commentId, int userId) {

        userValidatorUseCase.validateId(userId);

        commentValidatorUseCase.validateId(commentId);


        accessTokenAuthenticityUseCase.checkIfUserIdMatches(requestAccessToken, userRepository.findByUserIdAndDeleted(userId, false));

        commentRatingValidatorUseCase.validateId(commentId, userId);

        commentRatingRepository.deleteCommentRating(userId, commentId);

        int commentAuthorId = commentRepository.findByCommentAuthorAndDeleted(userRepository.findByUserIdAndDeleted(userId, false), false).getCommentAuthor().getUserId();

        calculateUserRatingUseCase.calculateUserRating(commentAuthorId);

        evaluateUserHonourableStatusUseCase.evaluateUserHonourableStatus(commentAuthorId);

        return FrontendCommentRatings.builder()
                .commentId(commentId)
                .upvoteCount(commentRatingRepository.getCountOfCommentRatingsByCommentIdAndWeight(commentId, 1))
                .downvoteCount(commentRatingRepository.getCountOfCommentRatingsByCommentIdAndWeight(commentId, -1))
                .build();
    }

    /**
     * @param userId
     * @param postUrl
     * @return
     */
    @Override
    public GetCommentRatingsResponse getCommentRatingsFromUserByPostUrl(@NotNull int userId, @NotBlank String postUrl) {

        userValidatorUseCase.validateId(userId);

        postValidator.validateUrl(postUrl);

        accessTokenAuthenticityUseCase.checkIfUserIdMatches(requestAccessToken, userRepository.findByUserIdAndDeleted(userId, false));

        PostEntity post = postRepository.findByPostUrl(postUrl);

        return GetCommentRatingsResponse.builder()
                .commentRatingsList(
                        commentRatingRepository.findAllCommentRatingsFromUserByPost(userId, post.getPostId())
                                .stream()
                                .map(CommentRatingConverter::convert)
                                .toList()
                )
                .build();
    }

    /**
     * @param postUrl
     * @return
     */
    @Override
    public List<FrontendCommentRatings> getCombinedRatingsCountForAllCommentsOnPost(String postUrl) {

        postValidator.validateUrl(postUrl);

        List<CommentEntity> commentsOnPost = commentRepository.getAllCommentsOnPost(postRepository.findByPostUrl(postUrl).getPostId());

        List<FrontendCommentRatings> listOfCombinedCommentRatings = new ArrayList<>();
        commentsOnPost.forEach(
                commentEntity -> {
                    int commentId = commentEntity.getCommentId();
                    int upvoteCount = commentRatingRepository.getCountOfCommentRatingsByCommentIdAndWeight(commentId, 1);
                    int downvoteCount = commentRatingRepository.getCountOfCommentRatingsByCommentIdAndWeight(commentId, -1);
                    listOfCombinedCommentRatings.add(new FrontendCommentRatings(commentId, upvoteCount, downvoteCount));
                }
        );

        return listOfCombinedCommentRatings;
    }
}
