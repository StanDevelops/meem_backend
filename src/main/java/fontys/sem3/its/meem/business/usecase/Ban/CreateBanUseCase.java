package fontys.sem3.its.meem.business.usecase.Ban;

import fontys.sem3.its.meem.domain.request.Ban.CreateBanRequest;

import javax.validation.constraints.NotBlank;

public interface CreateBanUseCase {
    void createBan(@NotBlank CreateBanRequest request);
}
