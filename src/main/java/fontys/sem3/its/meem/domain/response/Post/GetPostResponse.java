package fontys.sem3.its.meem.domain.response.Post;

import fontys.sem3.its.meem.domain.model.FrontendPost;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetPostResponse {
    private FrontendPost post;
}
