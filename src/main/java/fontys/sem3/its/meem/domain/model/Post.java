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
public class Post {
    private int postId;
    private int postAuthor;
    private int categoryId;
    private int groupId;
    private Timestamp datePosted;
    private String postTitle;
    private int postMedia;
    private Boolean postEdited;
    private String postUrl;
    private Boolean explicit;
    private Boolean deleted;
}
