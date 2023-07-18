package fontys.sem3.its.meem.domain.request.SortingGroup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

//done
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSortingGroupRequest {
    @NotBlank
    @Length(min = 3, max = 12)
    private String groupName;
    @NotNull
    private int groupRank;
}
