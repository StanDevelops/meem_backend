package fontys.sem3.its.meem.business.usecase.User;

import fontys.sem3.its.meem.domain.request.User.ChangePasswordRequest;

public interface ChangeUserPasswordUseCase {
    void changePassword(ChangePasswordRequest request);
}
