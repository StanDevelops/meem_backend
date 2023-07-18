package fontys.sem3.its.meem.business.usecase.User;

import fontys.sem3.its.meem.domain.request.User.CreateUserRequest;

public interface CreateUserUseCase {
    void createUser(CreateUserRequest request);
}
