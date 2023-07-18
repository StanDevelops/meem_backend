package fontys.sem3.its.meem.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostUploadMessage {
    private String postTitle;
    private String postUrl;
    private int authorId;
    private String authorUsername;
    private String authorPfpUrl;
}