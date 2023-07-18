package fontys.sem3.its.meem.business.usecase.User;

import fontys.sem3.its.meem.domain.request.Auth.LoginRequest;
import fontys.sem3.its.meem.domain.response.Auth.UserLoginResponse;

public interface LoginUserUseCase {
    UserLoginResponse LogInUser(LoginRequest request);
}
