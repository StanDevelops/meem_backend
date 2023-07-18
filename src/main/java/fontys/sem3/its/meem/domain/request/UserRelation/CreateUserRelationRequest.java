package fontys.sem3.its.meem.domain.request.UserRelation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

//done
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRelationRequest {
    @NotNull
    private int primaryUserId;
    @NotNull
    private int secondaryUserId;
    @NotBlank
    private String relationType;

}
