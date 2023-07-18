package fontys.sem3.its.meem.business.usecase.User;

import fontys.sem3.its.meem.domain.request.User.ChangeUsernameRequest;

public interface ChangeUsernameUseCase {
    void changeUsername(ChangeUsernameRequest request);
}
