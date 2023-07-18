package fontys.sem3.its.meem.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessToken {
    private String subject;
    private String authority;
    private int userId;

//    @JsonIgnore
//    public boolean hasRole(String roleName) {
//        if (!isMod) {
//            return false;
//        }
//        return roles.contains(roleName);
//    }
}