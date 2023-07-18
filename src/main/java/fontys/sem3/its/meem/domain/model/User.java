package fontys.sem3.its.meem.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

//done
@Data
@Builder
@AllArgsConstructor
//@NoArgsConstructor
public class User {
    private int userId;
    private String username;
    private String email;
    private String password;
    private Timestamp dateRegistered;
    private Character gender;
    private int userRating;
    private Boolean honourableUser;
    private UserRoleEnum userRole;
    private Boolean isBanned;
    private String profilePicture;
    private Boolean deleted;
}
