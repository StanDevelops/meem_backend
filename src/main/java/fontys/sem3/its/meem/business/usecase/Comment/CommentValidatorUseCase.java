package fontys.sem3.its.meem.business.usecase.Comment;

import fontys.sem3.its.meem.business.exception.InvalidIdentificationException;

public interface CommentValidatorUseCase {
    void validateId(int commentId) throws InvalidIdentificationException;
}
