package fontys.sem3.its.meem.business.usecase.Post;

import fontys.sem3.its.meem.business.exception.InvalidIdentificationException;
import fontys.sem3.its.meem.business.exception.UnauthorizedDataAccessException;
import fontys.sem3.its.meem.domain.model.FrontendPost;
import fontys.sem3.its.meem.domain.request.Post.CreatePostRequest;

public interface CreatePostUseCase {

    FrontendPost createPostWithExternalMedia(CreatePostRequest request) throws InvalidIdentificationException, UnauthorizedDataAccessException;
}
