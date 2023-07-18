package fontys.sem3.its.meem.business.usecase.Commandment;

import fontys.sem3.its.meem.domain.request.Commandment.CreateCommandmentRequest;

public interface CreateCommandmentUseCase {
    /**
     * @param request
     */
    void createCommandment(CreateCommandmentRequest request);
}
