package fontys.sem3.its.meem.business.service;

import fontys.sem3.its.meem.business.exception.InvalidAccessTokenException;
import fontys.sem3.its.meem.business.exception.UnauthorizedDataAccessException;
import fontys.sem3.its.meem.domain.model.AccessToken;
import fontys.sem3.its.meem.domain.model.UserRoleEnum;
import fontys.sem3.its.meem.persistence.entity.MediaEntity;
import fontys.sem3.its.meem.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class AccessTokenAuthenticityImplTest {
    @InjectMocks
    private AccessTokenAuthenticityImpl accessTokenAuthenticity;

    /**
     * @verifies return true when request user matches access token user
     * @see AccessTokenAuthenticityImpl#checkIfUserIdMatches(fontys.sem3.its.meem.domain.model.AccessToken, fontys.sem3.its.meem.persistence.entity.UserEntity)
     */
    @Test
    void checkIfUserIdMatches_shouldReturnTrueWhenRequestUserMatchesAccessTokenUser() {
        // Arrange
        UserEntity user = UserEntity.builder()
                .userId(10)
                .username("test_user")
                .email("test.me@meem.nl")
                .password("KJHjksaf897%%&hjkgds*_89")
                .dateRegistered(Timestamp.valueOf("2023-01-19 03:14:07"))
                .gender('M')
                .userRating(69)
                .honourableUser(false)
                .userRole(UserRoleEnum.REGULAR)
                .isBanned(true)
                .deleted(false)
                .profilePicture(MediaEntity.builder()
                        .mediaId(1)
                        .mediaAddress("/address")
                        .external(true)
                        .deleted(false)
                        .build())
                .build();

        AccessToken accessToken = AccessToken.builder()
                .userId(10)
                .subject("test_user")
                .authority("REGULAR")
                .build();

        boolean expectedResult = true;

        boolean actualResult;

        // Act

        actualResult = accessTokenAuthenticity.checkIfUserIdMatches(accessToken, user);

        // Assert

        assertEquals(expectedResult, actualResult);

    }

    /**
     * @verifies throw UnauthorizedDataAccessException when request user does not match access token user
     * @see AccessTokenAuthenticityImpl#checkIfUserIdMatches(fontys.sem3.its.meem.domain.model.AccessToken, fontys.sem3.its.meem.persistence.entity.UserEntity)
     */
    @Test
    void checkIfUserIdMatches_shouldThrowUnauthorizedDataAccessExceptionWhenRequestUserDoesNotMatchAccessTokenUser() throws UnauthorizedDataAccessException {
        // Arrange
        UserEntity user = UserEntity.builder()
                .userId(10)
                .username("test_user")
                .email("test.me@meem.nl")
                .password("KJHjksaf897%%&hjkgds*_89")
                .dateRegistered(Timestamp.valueOf("2023-01-19 03:14:07"))
                .gender('M')
                .userRating(69)
                .honourableUser(false)
                .userRole(UserRoleEnum.REGULAR)
                .isBanned(true)
                .deleted(false)
                .profilePicture(MediaEntity.builder()
                        .mediaId(1)
                        .mediaAddress("/address")
                        .external(true)
                        .deleted(false)
                        .build())
                .build();

        AccessToken accessToken = AccessToken.builder()
                .userId(100)
                .subject("test_user")
                .authority("REGULAR")
                .build();


        // Act

        UnauthorizedDataAccessException exception = assertThrows(UnauthorizedDataAccessException.class, () ->
                accessTokenAuthenticity.checkIfUserIdMatches(accessToken, user));

        // Assert

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());

        assertEquals("USER_ID_NOT_FROM_AUTHOR", exception.getReason());
    }

    /**
     * @verifies return true when request authority matches db user authority
     * @see AccessTokenAuthenticityImpl#checkIfAuthoritiesMatch(fontys.sem3.its.meem.persistence.entity.UserEntity, fontys.sem3.its.meem.domain.model.AccessToken)
     */
    @Test
    void checkIfAuthoritiesMatch_shouldReturnTrueWhenRequestAuthorityMatchesDbUserAuthority() {
        // Arrange
        UserEntity user = UserEntity.builder()
                .userId(10)
                .username("test_user")
                .email("test.me@meem.nl")
                .password("KJHjksaf897%%&hjkgds*_89")
                .dateRegistered(Timestamp.valueOf("2023-01-19 03:14:07"))
                .gender('M')
                .userRating(69)
                .honourableUser(false)
                .userRole(UserRoleEnum.REGULAR)
                .isBanned(true)
                .deleted(false)
                .profilePicture(MediaEntity.builder()
                        .mediaId(1)
                        .mediaAddress("/address")
                        .external(true)
                        .deleted(false)
                        .build())
                .build();

        AccessToken accessToken = AccessToken.builder()
                .userId(10)
                .subject("test_user")
                .authority("REGULAR")
                .build();

        boolean expectedResult = true;

        boolean actualResult;

        // Act

        actualResult = accessTokenAuthenticity.checkIfAuthoritiesMatch(user, accessToken);

        // Assert

        assertEquals(expectedResult, actualResult);

    }

    /**
     * @verifies throw UnauthorizedDataAccessException when request user does not match access token user
     * @see AccessTokenAuthenticityImpl#checkIfAuthoritiesMatch(fontys.sem3.its.meem.persistence.entity.UserEntity, fontys.sem3.its.meem.domain.model.AccessToken)
     */
    @Test
    void checkIfAuthoritiesMatch_shouldThrowInvalidAccessTokenExceptionWhenRequestUserDoesNotMatchAccessTokenUser() throws InvalidAccessTokenException {
        // Arrange
        UserEntity user = UserEntity.builder()
                .userId(100)
                .username("test_user")
                .email("test.me@meem.nl")
                .password("KJHjksaf897%%&hjkgds*_89")
                .dateRegistered(Timestamp.valueOf("2023-01-19 03:14:07"))
                .gender('M')
                .userRating(69)
                .honourableUser(false)
                .userRole(UserRoleEnum.REGULAR)
                .isBanned(true)
                .deleted(false)
                .profilePicture(MediaEntity.builder()
                        .mediaId(1)
                        .mediaAddress("/address")
                        .external(true)
                        .deleted(false)
                        .build())
                .build();

        AccessToken accessToken = AccessToken.builder()
                .userId(100)
                .subject("test_user")
                .authority("MOD")
                .build();


        // Act

        InvalidAccessTokenException exception = assertThrows(InvalidAccessTokenException.class, () ->
                accessTokenAuthenticity.checkIfAuthoritiesMatch(user, accessToken));

        // Assert

        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());

        assertEquals("AUTHORITY_NOT_MATCHING_USER_AUTHORITY", exception.getReason());
    }

    /**
     * @verifies throw UnauthorizedDataAccessException _when user has banned authority
     * @see AccessTokenAuthenticityImpl#checkIfBanned(AccessToken)
     */
    @Test
    void checkIfBanned_shouldThrowUnauthorizedDataAccessException_whenUserHasBannedAuthority() throws Exception {
        //arrange
        AccessToken accessToken = AccessToken.builder()
                .userId(100)
                .subject("test_user")
                .authority("BANNED")
                .build();

        //act
        UnauthorizedDataAccessException exception = assertThrows(UnauthorizedDataAccessException.class, () -> accessTokenAuthenticity.checkIfBanned(accessToken));

        //assert
        assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());

        assertEquals("USER_IS_BANNED", exception.getReason());
    }
}
