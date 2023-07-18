package fontys.sem3.its.meem.business.usecase.AccessToken;

import fontys.sem3.its.meem.business.exception.InvalidAccessTokenException;
import fontys.sem3.its.meem.business.exception.UnauthorizedDataAccessException;
import fontys.sem3.its.meem.domain.model.AccessToken;
import fontys.sem3.its.meem.persistence.entity.UserEntity;

public interface AccessTokenAuthenticityUseCase {
    boolean checkIfUserIdMatches(AccessToken accessToken, UserEntity user) throws UnauthorizedDataAccessException;

    boolean checkIfAuthoritiesMatch(UserEntity user, AccessToken accessToken) throws InvalidAccessTokenException;

    void checkIfBanned(AccessToken requestToken) throws UnauthorizedDataAccessException;
}
