package fontys.sem3.its.meem.domain.request.Ban;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

//done
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBanRequest {
    @NotNull
    private int userId;
    @NotNull
    private int modId;
    @NotNull
    private int commandmentNr;

}
