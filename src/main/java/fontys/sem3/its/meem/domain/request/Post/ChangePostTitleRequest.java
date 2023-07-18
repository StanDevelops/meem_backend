package fontys.sem3.its.meem.domain.request.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePostTitleRequest {
    private int postId;
    private String newTitle;
}
