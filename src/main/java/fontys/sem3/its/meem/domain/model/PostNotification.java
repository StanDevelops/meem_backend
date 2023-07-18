package fontys.sem3.its.meem.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

//done
@Data
@Builder
@AllArgsConstructor
public class PostNotification {
    private String postTitle;
    private String postUrl;
    private int authorId;
    private String authorUsername;
    private String authorPfpUrl;
}
