package fontys.sem3.its.meem.business.usecase.AccessToken;

import fontys.sem3.its.meem.domain.model.AccessToken;

public interface AccessTokenDecodingUseCase {
    AccessToken decode(String accessTokenEncoded);
}
