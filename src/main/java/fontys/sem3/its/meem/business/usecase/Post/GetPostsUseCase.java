package fontys.sem3.its.meem.business.usecase.Post;

import fontys.sem3.its.meem.domain.response.Post.GetPostsResponse;

public interface GetPostsUseCase {
//    GetPostsResponse getPosts();
//    GetPostsResponse getPostsByCategory(int categoryId);
    GetPostsResponse getPostsBySortingGroup(int groupId);
    GetPostsResponse getPostsBySortingGroupAndCategory(int groupId, int categoryId);
//    GetPostsResponse getPostsByAuthor(int authorId);
}
