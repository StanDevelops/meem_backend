package fontys.sem3.its.meem.business.service;

import fontys.sem3.its.meem.business.exception.InvalidCredentialsException;
import fontys.sem3.its.meem.business.usecase.AccessToken.AccessTokenEncodingUseCase;
import fontys.sem3.its.meem.business.usecase.Ban.CheckBanExpirationUseCase;
import fontys.sem3.its.meem.business.usecase.User.LoginUserUseCase;
import fontys.sem3.its.meem.business.usecase.User.UserValidatorUseCase;
import fontys.sem3.its.meem.domain.model.AccessToken;
import fontys.sem3.its.meem.domain.model.UserRoleEnum;
import fontys.sem3.its.meem.domain.request.Auth.LoginRequest;
import fontys.sem3.its.meem.domain.response.Auth.UserLoginResponse;
import fontys.sem3.its.meem.persistence.entity.UserEntity;
import fontys.sem3.its.meem.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Service
@AllArgsConstructor
public class AuthServiceImpl implements
        LoginUserUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserValidatorUseCase userValidator;
    private final AccessTokenEncodingUseCase accessTokenEncoder;
    private final CheckBanExpirationUseCase checkBanExpirationUseCase;
//    private AccessToken requestAccessToken;

    /**
     * @param request login request, requires user email and password
     * @return UserLoginResponse
     * @should return user login response when authentication is successful.
     * @should check ban expiration if user is banned and return user login response when authentication is successful.
     * @should throw Invalid Credentials Exception when password is incorrect.
     * @should throw Invalid Identification Exception when email is incorrect.
     */
    @Override
    public UserLoginResponse LogInUser(@NotNull LoginRequest request) {

        userValidator.validateEmail(request.getEmail());

        UserEntity returnedUser = userRepository.findUserByEmailAndDeleted(request.getEmail(), false);

        /*
        matchesPassword(URLDecoder.decode(request.getPassword(), StandardCharsets.UTF_8)
         */

        if (matchesPassword(request.getPassword(), returnedUser.getPassword())) {
            /**
             * check if the user is banned
             * if user is banned, check if ban has expired
             */
            if (returnedUser.getUserRole() == UserRoleEnum.BANNED) {

                checkBanExpirationUseCase.checkBanExpiration(returnedUser.getUserId());

            }

            String accessToken = generateAccessToken(returnedUser);

            return UserLoginResponse.builder().accessToken(accessToken).build();

        } else {

            throw new InvalidCredentialsException("WRONG_PASSWORD");

        }

    }

    /**
     * @param rawPassword
     * @param encodedPassword
     * @return boolean
     * @should return true when passwords match.
     * @should return false when passwords do not match.
     */
    private boolean matchesPassword(@NotBlank String rawPassword, @NotBlank String encodedPassword) {

        return passwordEncoder.matches(rawPassword, encodedPassword);

    }

    /**
     * @param user
     * @return String
     * @should generate access token, encode and return string
     */
    private String generateAccessToken(@NotNull UserEntity user) {

        String authority = "";

        switch (user.getUserRole()) {
            case ADMIN -> authority = "ADMIN";
            case REGULAR -> authority = "REGULAR";
            case BANNED -> authority = "BANNED";
            case MOD -> authority = "MOD";
        }

        return accessTokenEncoder.encode(
                AccessToken.builder()
                        .subject(user.getUsername())
                        .authority(authority)
                        .userId(user.getUserId())
                        .build()
        );

    }

}
