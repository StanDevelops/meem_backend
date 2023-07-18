package fontys.sem3.its.meem.business.service;

import fontys.sem3.its.meem.business.exception.InvalidCredentialsException;
import fontys.sem3.its.meem.business.exception.InvalidIdentificationException;
import fontys.sem3.its.meem.business.usecase.AccessToken.AccessTokenEncodingUseCase;
import fontys.sem3.its.meem.business.usecase.Ban.CheckBanExpirationUseCase;
import fontys.sem3.its.meem.business.usecase.User.UserValidatorUseCase;
import fontys.sem3.its.meem.domain.model.AccessToken;
import fontys.sem3.its.meem.domain.model.UserRoleEnum;
import fontys.sem3.its.meem.domain.request.Auth.LoginRequest;
import fontys.sem3.its.meem.domain.response.Auth.UserLoginResponse;
import fontys.sem3.its.meem.persistence.entity.MediaEntity;
import fontys.sem3.its.meem.persistence.entity.UserEntity;
import fontys.sem3.its.meem.persistence.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    @Mock
    private AccessToken requestAccessToken;
    @Mock
    private UserValidatorUseCase userValidator;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AccessTokenEncodingUseCase accessTokenEncodingUseCase;
    @Mock
    private CheckBanExpirationUseCase checkBanExpirationUseCase;
    @InjectMocks
    private AuthServiceImpl authService;


    /**
     * @verifies check ban expiraten if user is banned and return user login response when authentication is succsessful.
     * @see AuthServiceImpl#LogInUser(LoginRequest)
     */
    @Test
    void LogInUser_shouldCheckBanExpirationIfUserIsBannedAndReturnUserLoginResponseWhenAuthenticationIsSuccessful() {
        // Arrange

        LoginRequest request = LoginRequest.builder().email("test.me@meem.nl").password("parola123").build();

        when(userValidator.validateEmail(request.getEmail())).thenReturn(true);

        UserEntity returnedUser = UserEntity.builder()
                .userId(10)
                .username("test_user")
                .email("test.me@meem.nl")
                .password("KJHjksaf897%%&hjkgds*_89")
                .dateRegistered(Timestamp.valueOf("2023-01-19 03:14:07"))
                .gender('M')
                .userRating(69)
                .honourableUser(false)
                .userRole(UserRoleEnum.BANNED)
                .isBanned(true)
                .deleted(false)
                .profilePicture(MediaEntity.builder()
                        .mediaId(1)
                        .mediaAddress("/address")
                        .external(true)
                        .deleted(false)
                        .build())
                .build();

        when(userRepository.findUserByEmailAndDeleted(request.getEmail(), false)).thenReturn(returnedUser);

        requestAccessToken = AccessToken.builder()
                .userId(10)
                .subject("test_user")
                .authority("BANNED")
                .build();

        when(passwordEncoder.matches(request.getPassword(), returnedUser.getPassword())).thenReturn(true);

        when(accessTokenEncodingUseCase.encode(requestAccessToken)).thenReturn("accesstoken");

        UserLoginResponse expectedResponse = UserLoginResponse.builder().accessToken("accesstoken").build();

        // Act
        UserLoginResponse actualResponse = authService.LogInUser(request);

        // Assert

        assertEquals(expectedResponse, actualResponse);

        verify(userValidator).validateEmail(request.getEmail());

        verify(userRepository).findUserByEmailAndDeleted(request.getEmail(), false);

        verify(passwordEncoder).matches(request.getPassword(), returnedUser.getPassword());

        verify(checkBanExpirationUseCase).checkBanExpiration(returnedUser.getUserId());

        verify(accessTokenEncodingUseCase).encode(requestAccessToken);

    }

    /**
     * @verifies throw Invalid Credentials Exception when password is incorrect.
     * @see AuthServiceImpl#LogInUser(fontys.sem3.its.meem.domain.request.Auth.LoginRequest)
     */
    @Test
    void LogInUser_shouldThrowInvalidCredentialsExceptionWhenPasswordIsIncorrect() {

        // Arrange
        LoginRequest request = LoginRequest.builder().email("test.me@meem.nl").password("parola123").build();

        when(userValidator.validateEmail(request.getEmail())).thenReturn(true);

        UserEntity returnedUser = UserEntity.builder()
                .userId(10)
                .username("test_user")
                .email("test.me@meem.nl")
                .password("KJHjksaf897%%&hjkgds*_89")
                .dateRegistered(Timestamp.valueOf("2023-01-19 03:14:07"))
                .gender('M')
                .userRating(69)
                .honourableUser(false)
                .userRole(UserRoleEnum.BANNED)
                .isBanned(true)
                .deleted(false)
                .profilePicture(MediaEntity.builder()
                        .mediaId(1)
                        .mediaAddress("/address")
                        .external(true)
                        .deleted(false)
                        .build())
                .build();

        when(userRepository.findUserByEmailAndDeleted(request.getEmail(), false)).thenReturn(returnedUser);

        when(passwordEncoder.matches(request.getPassword(), returnedUser.getPassword())).thenReturn(false);

        InvalidCredentialsException exception;

        // Act

        exception = assertThrows(InvalidCredentialsException.class, () -> authService.LogInUser(request));

        // Assert

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

        assertEquals("WRONG_PASSWORD", exception.getReason());

        verify(userValidator).validateEmail(request.getEmail());

        verify(userRepository).findUserByEmailAndDeleted(request.getEmail(), false);

        verify(passwordEncoder).matches(request.getPassword(), returnedUser.getPassword());

    }


    /**
     * @verifies throw Invalid Identification Exception when email is incorrect.
     * @see AuthServiceImpl#LogInUser(fontys.sem3.its.meem.domain.request.Auth.LoginRequest)
     */
    @Test
    void LogInUser_shouldThrowInvalidIdentificationExceptionWhenEmailIsIncorrect() {

        // Arrange
        LoginRequest request = LoginRequest.builder().email("test.me@meem.nl").password("parola123").build();

        when(userValidator.validateEmail(request.getEmail())).thenThrow(new InvalidIdentificationException());

        InvalidIdentificationException exception;

        // Act

        exception = assertThrows(InvalidIdentificationException.class, () -> authService.LogInUser(request));

        // Assert

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());

        assertEquals("INVALID_IDENTIFIER", exception.getReason());

        verify(userValidator).validateEmail(request.getEmail());

    }

    /**
     * @verifies return true when passwords match.
     */
    @Test
    void matchesPassword_shouldReturnTrueWhenPasswordsMatch() {

        // Arrange
        String rawPassword = "parola123";

        String encodedPassword = "KJHjksaf897%%&hjkgds*_89";

        boolean actualResult;

        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

        // Act

        actualResult = passwordEncoder.matches(rawPassword, encodedPassword);

        // Assert

        assertTrue(actualResult);

        verify(passwordEncoder).matches(rawPassword, encodedPassword);
    }

    /**
     * @verifies return false when passwords do not match.
     */
    @Test
    void matchesPassword_shouldReturnFalseWhenPasswordsDoNotMatch() {

        // Arrange
        String rawPassword = "parola123";

        String encodedPassword = "KJHjksaf897%%&hjkgds*_89";

        boolean actualResult;

        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        // Act
        actualResult = passwordEncoder.matches(rawPassword, encodedPassword);

        // Assert
        assertFalse(actualResult);

        verify(passwordEncoder).matches(rawPassword, encodedPassword);

    }

    /**
     * @verifies generate access token, encode and return string
     */
    @Test
    void generateAccessToken_shouldGenerateAccessTokenEncodeAndReturnString() {

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

        String expectedEncodedAccessToken = "random#as#";

        String actualEncodedAccessToken;

        requestAccessToken = AccessToken.builder()
                .subject(user.getUsername())
                .authority(user.getUserRole().name())
                .userId(user.getUserId())
                .build();

        when(accessTokenEncodingUseCase.encode(requestAccessToken)).thenReturn(expectedEncodedAccessToken);

        // Act

        actualEncodedAccessToken = accessTokenEncodingUseCase.encode(requestAccessToken);

        // Assert

        assertEquals(expectedEncodedAccessToken, actualEncodedAccessToken);

        verify(accessTokenEncodingUseCase).encode(requestAccessToken);

    }
}
