package fontys.sem3.its.meem.business.service;

import fontys.sem3.its.meem.business.exception.InvalidAccessTokenException;
import fontys.sem3.its.meem.business.exception.UnauthorizedDataAccessException;
import fontys.sem3.its.meem.business.service.converter.CommentConverter;
import fontys.sem3.its.meem.business.usecase.ActivityHistory.CreateActivityHistoryUseCase;
import fontys.sem3.its.meem.business.usecase.Comment.*;
import fontys.sem3.its.meem.business.usecase.Post.PostValidatorUseCase;
import fontys.sem3.its.meem.business.usecase.User.UserValidatorUseCase;
import fontys.sem3.its.meem.domain.model.AccessToken;
import fontys.sem3.its.meem.domain.model.ActivityTypeEnum;
import fontys.sem3.its.meem.domain.model.Comment;
import fontys.sem3.its.meem.domain.model.FrontendComment;
import fontys.sem3.its.meem.domain.request.Comment.ChangeCommentRequest;
import fontys.sem3.its.meem.domain.request.Comment.CreateCommentReplyRequest;
import fontys.sem3.its.meem.domain.request.Comment.CreateCommentRequest;
import fontys.sem3.its.meem.domain.response.Comment.GetCommentsResponse;
import fontys.sem3.its.meem.persistence.entity.CommentEntity;
import fontys.sem3.its.meem.persistence.entity.UserEntity;
import fontys.sem3.its.meem.persistence.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements ReplyToCommentUseCase, ChangeCommentUseCase, CreateCommentUseCase, DeleteCommentUseCase, GetCommentsUseCase {
    private AccessToken requestAccessToken;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostValidatorUseCase postValidator;
    private final UserValidatorUseCase userValidator;
    private final CommentRatingRepository commentRatingRepository;
    private final CreateActivityHistoryUseCase createActivityHistoryUseCase;
    private final MediaRepository mediaRepository;
    private final CommentValidatorUseCase commentValidatorUseCase;

    private FrontendComment buildCombinedComment(Comment comment) {

        UserEntity commentAuthor = userRepository.findByUserIdAndDeleted(comment.getCommentAuthor(), false);

        return FrontendComment.builder()
                .commentId(comment.getCommentId())
                .commentBody(comment.getCommentBody())
                .authorId(comment.getCommentAuthor())
                .authorName(commentAuthor.getUsername())
                .authorIsHonourable(commentAuthor.getHonourableUser())
                .commentEdited(comment.getCommentEdited())
                .dateCommented(comment.getDateCommented())
                .parentCommentId(comment.getParentCommentId())
                .pfpUrl(commentAuthor.getProfilePicture().getMediaAddress())
                .depth(comment.getDepth())
                .explicit(comment.getExplicit())
                .postId(comment.getPostId())
                .commentEdited(comment.getCommentEdited())
                .build();
    }

    private void checkIfUserIdMatches(UserEntity user) {
        if (requestAccessToken.getUserId() != user.getUserId()) {
            throw new UnauthorizedDataAccessException("USER_ID_NOT_FROM_POST_AUTHOR");
        }
    }

    private void checkIfBanned() {
        if (Objects.equals(requestAccessToken.getAuthority(), "BANNED")) {
            throw new UnauthorizedDataAccessException("USER_IS_BANNED");
        }
    }

    private void checkIfAuthoritiesMatch() {
        if (!Objects.equals(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false).getUserRole().toString(), requestAccessToken.getAuthority())) {
            throw new InvalidAccessTokenException("AUTHORITY_NOT_MATCHING_USER_AUTHORITY");
        }
    }

    @Override
    public void changeComment(@NotNull ChangeCommentRequest request) {

        commentValidatorUseCase.validateId(request.getCommentId());

        checkIfAuthoritiesMatch();

        checkIfBanned();

        UserEntity commentAuthor = commentRepository.findByCommentIdAndDeleted(request.getCommentId(), false).getCommentAuthor();

        checkIfUserIdMatches(commentAuthor);

        commentRepository.updateCommentBody(request.getCommentId(), request.getNewText());

        createActivityHistoryUseCase.createActivityHistoryForUserInteractionsWithComment(
                commentAuthor,
                ActivityTypeEnum.EDITED,
                commentRepository.findByCommentIdAndDeleted(request.getCommentId(), false)
        );
    }

    /**
     * @param request
     */
    @Override
    public FrontendComment createComment(@NotNull CreateCommentRequest request) {

        postValidator.validateId(request.getPostId());

//        checkIfAuthoritiesMatch();

        checkIfBanned();

        checkIfUserIdMatches(userRepository.findByUserIdAndDeleted(request.getAuthorId(), false));

        CommentEntity tempComment = CommentEntity.builder()
                .commentAuthor(userRepository.findByUserIdAndDeleted(request.getAuthorId(), false))
                .commentBody(request.getCommentBody())
                .explicit(request.getExplicit())
                .post(postRepository.findByPostIdAndDeleted(request.getPostId(), false))
                .build();

        return buildCombinedComment(CommentConverter.convert(tempComment));
    }

    /**
     * @param commentId
     */
    @Override
    public void deleteComment(int commentId) {

    }

    /**
     * @param postUrl
     * @return
     */
    @Override
    public GetCommentsResponse getCommentsByPostUrl(@NotBlank String postUrl) {
        List<CommentEntity> returnedComments = commentRepository.getAllCommentsOnPost(postRepository.findByPostUrl(postUrl).getPostId());
        return null;
        //todo
    }

    /**
     * @param request
     */
    @Override
    public void createCommentReply(@NotNull CreateCommentReplyRequest request) {
        //todo
    }
}
