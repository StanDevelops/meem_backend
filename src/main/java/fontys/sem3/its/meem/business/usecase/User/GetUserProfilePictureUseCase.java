package fontys.sem3.its.meem.business.usecase.User;

import fontys.sem3.its.meem.domain.response.User.GetUserProfilePictureResponse;

public interface GetUserProfilePictureUseCase {
    GetUserProfilePictureResponse getUserPFP(int userId);
}
