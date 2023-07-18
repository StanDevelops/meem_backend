package fontys.sem3.its.meem.business.usecase.User;

import fontys.sem3.its.meem.domain.response.User.GetUserResponse;

public interface GetUserUseCase {
    GetUserResponse getUserById(int userId);
    GetUserResponse getUserByUsername(String username);
    GetUserResponse getUserByEmail(String email);
}
