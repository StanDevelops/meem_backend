package fontys.sem3.its.meem.domain.response.Post;

import fontys.sem3.its.meem.domain.model.FrontendPost;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetPostsResponse {
    private List<FrontendPost> posts;
}
