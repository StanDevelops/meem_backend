package fontys.sem3.its.meem.business.validator;

import fontys.sem3.its.meem.business.exception.InvalidIdentificationException;
import fontys.sem3.its.meem.business.usecase.CommentRating.CommentRatingValidatorUseCase;
import fontys.sem3.its.meem.persistence.repository.CommentRatingRepository;
import fontys.sem3.its.meem.persistence.repository.CommentRepository;
import fontys.sem3.its.meem.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@AllArgsConstructor
public class CommentRatingValidator implements CommentRatingValidatorUseCase {
    private final CommentRatingRepository commentRatingRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    /**
     * @param commentId
     * @param userId
     * @throws InvalidIdentificationException
     */
    @Override
    public void validateId(@NotNull int commentId, @NotNull int userId) throws InvalidIdentificationException {
        if (!commentRatingRepository.existsByCommentIdAndUserId(
                commentRepository.findByCommentIdAndDeleted(commentId, false),
                userRepository.findByUserIdAndDeleted(userId, false)
        )) {
            throw new InvalidIdentificationException("NON_EXISTENT_RATING");
        }
    }
}
