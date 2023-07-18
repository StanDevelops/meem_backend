package fontys.sem3.its.meem.domain.model;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class FrontendPost {
    private int postId;
    private int authorId;
    private int categoryId;
    private int groupId;
    private Timestamp datePosted;
    private String postTitle;
    private String postMedia;
    private Boolean postEdited;
    private String authorName;
    private String pfpUrl;
    private String categoryName;
    private Boolean explicit;
    private Boolean authorIsHonourable;
    private String postUrl;
}