package fontys.sem3.its.meem.business.usecase.Post;


import fontys.sem3.its.meem.domain.response.Post.GetPostResponse;

public interface GetPostUseCase {
    GetPostResponse getPostById(int postId);
    GetPostResponse getPostByUrl(String postUrl);

}
