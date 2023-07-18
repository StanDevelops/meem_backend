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
public class ResetPasswordRequest {
    @NotBlank
    private int userId;
    @NotBlank
    private int authCode;
    @NotBlank
    private String newPassword;
    @NotBlank
    private String newPasswordRepeat;
}
