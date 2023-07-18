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
public class Comment {
    private int commentId;
    private int commentAuthor;
    private int postId;
    private Timestamp dateCommented;
    private String commentBody;
    private Boolean commentEdited;
    private Boolean explicit;
    private int parentCommentId;
    private int depth;
    private boolean deleted;
}
