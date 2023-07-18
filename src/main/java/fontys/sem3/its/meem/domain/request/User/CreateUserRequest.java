package fontys.sem3.its.meem.domain.request.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    @NotBlank
    @Length(min = 3, max = 21)
    private String username;
    @NotBlank
    @Length(min = 5, max = 50)
    private String email;
    @NotBlank
    private String password;
    //    @NotNull
//    private Timestamp dateRegistered;
    @NotBlank
    private Character gender;
//    @NotNull
//    private int userRating;
//    @NotNull
//    private Boolean honourableUser;
//    @NotNull
//    private Boolean modPrivilege;
//    @NotNull
//    private Boolean isBanned;
//    @NotBlank
//    private String profilePicture;
}
