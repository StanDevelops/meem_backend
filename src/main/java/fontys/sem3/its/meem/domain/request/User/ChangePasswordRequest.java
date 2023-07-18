package fontys.sem3.its.meem.domain.request.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {
    @NotBlank
    private int userId;
    @NotBlank
    private String oldPassword;
    @NotBlank
    private String newPassword;
}
