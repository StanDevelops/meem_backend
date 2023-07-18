package fontys.sem3.its.meem.domain.response.User;

import fontys.sem3.its.meem.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUserResponse {
    private User user;
}
