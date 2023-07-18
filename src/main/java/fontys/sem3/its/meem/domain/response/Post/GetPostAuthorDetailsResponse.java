package fontys.sem3.its.meem.domain.response.Post;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetPostAuthorDetailsResponse {
    private String authorName;
    private String pfpUrl;
}
