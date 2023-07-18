package fontys.sem3.its.meem.domain.response.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUserProfilePictureResponse {
    private String pfpUrl;
}
