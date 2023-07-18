package fontys.sem3.its.meem.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

//done
@Data
@Builder
@AllArgsConstructor
//@NoArgsConstructor
public class FrontendComment {
    private int commentId;
    private int authorId;
    private int postId;
    private Timestamp dateCommented;
    private String commentBody;
    private Boolean commentEdited;
    private int parentCommentId;
    private String authorName;
    private String pfpUrl;
    private Boolean explicit;
    private Boolean authorIsHonourable;
    private int depth;
}
