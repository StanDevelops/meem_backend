package fontys.sem3.its.meem.business.usecase.Post;

import fontys.sem3.its.meem.business.exception.InvalidIdentificationException;
import fontys.sem3.its.meem.business.exception.InvalidUrlException;

public interface PostValidatorUseCase {
    void validateId(int postId) throws InvalidIdentificationException;

    void validateUrl(String postUrl) throws InvalidUrlException;
}
