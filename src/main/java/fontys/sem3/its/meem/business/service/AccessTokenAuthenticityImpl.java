package fontys.sem3.its.meem.business.service;

import fontys.sem3.its.meem.business.exception.InvalidAccessTokenException;
import fontys.sem3.its.meem.business.exception.UnauthorizedDataAccessException;
import fontys.sem3.its.meem.business.usecase.AccessToken.AccessTokenAuthenticityUseCase;
import fontys.sem3.its.meem.domain.model.AccessToken;
import fontys.sem3.its.meem.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AccessTokenAuthenticityImpl implements AccessTokenAuthenticityUseCase {
    /**
     * @param accessToken
     * @param user
     * @return
     * @throws UnauthorizedDataAccessException
     * @should return true when request user matches access token user
     * @should throw UnauthorizedDataAccessException when request user does not match access token user
     */
    @Override
    public boolean checkIfUserIdMatches(AccessToken accessToken, UserEntity user) throws UnauthorizedDataAccessException {

        if (accessToken.getUserId() != user.getUserId()) {

            throw new UnauthorizedDataAccessException("USER_ID_NOT_FROM_AUTHOR");

        }

        return true;

    }

    /**
     * @param user
     * @param accessToken
     * @return
     * @throws InvalidAccessTokenException
     * @should return true when request authority matches db user authority
     * @should throw InvalidAccessTokenException when request user does not match access token user
     */
    @Override
    public boolean checkIfAuthoritiesMatch(UserEntity user, AccessToken accessToken) throws InvalidAccessTokenException {

        if (!Objects.equals(user.getUserRole().toString(), accessToken.getAuthority())) {

            throw new InvalidAccessTokenException("AUTHORITY_NOT_MATCHING_USER_AUTHORITY");

        }

        return true;

    }

    /**
     * @param requestToken
     * @throws UnauthorizedDataAccessException
     * @should throw UnauthorizedDataAccessException _when user has banned authority
     */
    @Override
    public void checkIfBanned(AccessToken requestToken) throws UnauthorizedDataAccessException {
        if (Objects.equals(requestToken.getAuthority(), "BANNED")) {
            throw new UnauthorizedDataAccessException("USER_IS_BANNED");
        }
    }
}
