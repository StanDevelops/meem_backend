package fontys.sem3.its.meem.business.validator;

import fontys.sem3.its.meem.business.exception.InvalidIdentificationException;
import fontys.sem3.its.meem.business.usecase.Comment.CommentValidatorUseCase;
import fontys.sem3.its.meem.persistence.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@AllArgsConstructor
public class CommentValidator implements CommentValidatorUseCase {
    private final CommentRepository commentRepository;

    public void validateId(@NotNull int commentId) throws InvalidIdentificationException {
        if (!commentRepository.existsById(commentId)) {
            throw new InvalidIdentificationException();
        }
    }
}
