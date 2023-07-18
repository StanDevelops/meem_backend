package fontys.sem3.its.meem.domain.request.UserRelation;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

//done
@Data
@Builder
public class ChangeUserRelationRequest {
    @NotNull
    private int primaryUserId;
    @NotNull
    private int secondaryUserId;
    @NotBlank
    private String newRelationType;

}
