package fontys.sem3.its.meem.domain.request.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeUsernameRequest {
    @NotNull
    private int userId;
    @NotBlank
    private String newUsername;

}
