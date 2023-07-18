package fontys.sem3.its.meem.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FrontendUserRelation {
    private int relationId;
    private int primaryUserId;
    private int secondaryUserId;
    private String secondaryUserUsername;
    private String secondaryUserPfpUrl;
    private String relationType;
}