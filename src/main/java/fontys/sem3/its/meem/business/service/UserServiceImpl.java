package fontys.sem3.its.meem.business.service;

import fontys.sem3.its.meem.business.exception.*;
import fontys.sem3.its.meem.business.service.converter.UserConverter;
import fontys.sem3.its.meem.business.usecase.AccessToken.AccessTokenAuthenticityUseCase;
import fontys.sem3.its.meem.business.usecase.User.*;
import fontys.sem3.its.meem.business.validator.UserValidator;
import fontys.sem3.its.meem.domain.model.AccessToken;
import fontys.sem3.its.meem.domain.model.UserRoleEnum;
import fontys.sem3.its.meem.domain.request.User.ChangePasswordRequest;
import fontys.sem3.its.meem.domain.request.User.ChangeUsernameRequest;
import fontys.sem3.its.meem.domain.request.User.CreateUserRequest;
import fontys.sem3.its.meem.domain.response.User.GetUserProfilePictureResponse;
import fontys.sem3.its.meem.domain.response.User.GetUserResponse;
import fontys.sem3.its.meem.persistence.entity.UserEntity;
import fontys.sem3.its.meem.persistence.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Objects;


@Service
@AllArgsConstructor
public class UserServiceImpl implements
        GetUserUseCase,
        DeleteUserUseCase,
        CreateUserUseCase,
        ChangeUsernameUseCase,
        SetUserAsBannedUseCase,
        SetUserAsUnbannedUseCase,
        CalculateUserRatingUseCase,
        ChangeUserPasswordUseCase,
        GetUserProfilePictureUseCase,
        EvaluateUserHonourableStatusUseCase,
        GiveUserModeratorPrivilegesUseCase,
        TakeAwayUserModeratorPrivilegesUseCase {
    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;
    private AccessToken requestAccessToken;
    private final MediaRepository mediaRepository;
    private final BanRepository banRepository;
    private final PostRatingRepository postRatingRepository;
    private final CommentRatingRepository commentRatingRepository;
    private final AccessTokenAuthenticityUseCase accessTokenAuthenticityUseCase;
    private final UserRelationRepository userRelationRepository;


    @Transactional
    @Override
    public void changePassword(@NotNull ChangePasswordRequest request) {

        // validate if given ID belongs to an entity from DB
        //throws an 'InvalidIdentificationException'
        userValidator.validateId(request.getUserId());
        /* check if the request has been sent by the user whose password has to be changed */
        UserEntity user = userRepository.findByUserIdAndDeleted(request.getUserId(), false);

        accessTokenAuthenticityUseCase.checkIfUserIdMatches(requestAccessToken, user);

        updatePasswordField(request);
    }

    private int updatePasswordField(@NotNull ChangePasswordRequest request) {

        return userRepository.changeUserPassword(request.getNewPassword(), request.getUserId(), request.getOldPassword());

    }

    @Transactional
    @Override
    public void changeUsername(@NotBlank ChangeUsernameRequest request) {
//        if (Objects.equals(request.getNewUsername().replaceAll("\\s+", ""), "") ||
//                request.getUserId() <= 0) {
//            throw new EmptyFieldException(); //empty field detected, throw a hissy.
//        }

        // validate if given ID belongs to an entity from DB
        //throws an 'InvalidIdentificationException'
        userValidator.validateId(request.getUserId());
        /* check if the request has been sent by the user whose password has to be changed */
        UserEntity user = userRepository.findByUserIdAndDeleted(request.getUserId(), false);

//        accessTokenAuthenticityUseCase.checkIfAuthoritiesMatch(user, requestAccessToken);

        accessTokenAuthenticityUseCase.checkIfUserIdMatches(requestAccessToken, user);

        updateUsernameField(request);
    }

    private int updateUsernameField(@NotNull ChangeUsernameRequest request) {

        return userRepository.changeUsername(request.getNewUsername(), request.getUserId());

    }

    /**
     * @param request
     * @throws EmailAlreadyInUseException
     * @throws UsernameAlreadyInUseException
     * @throws InvalidRequestFieldException
     * @should throw InvalidRequestFieldException_ when gender field does not match the cases
     * @should throw UsernameAlreadyInUseException_ when another user already has the username from the request
     * @should throw EmailAlreadyInUseException_ when another user already has the email from the request
     * @should create a user entity and save it_ when checks pass
     */
    @Override
    public void createUser(@NotNull CreateUserRequest request) throws EmailAlreadyInUseException, UsernameAlreadyInUseException, InvalidRequestFieldException {
        UserEntity tempUser = null;

        if (userRepository.existsUserEntityByEmailAndDeleted(request.getEmail(), false)) { //checks if a user, that hasn't been soft deleted, already exists with that email
            throw new EmailAlreadyInUseException();
        }
        if (userRepository.existsByUsernameAndDeleted(request.getUsername(), false)) { //checks if a user, that hasn't been soft deleted, already exists with that username
            throw new UsernameAlreadyInUseException();
        }
            /*
            the switch statement is used to assign a default avatar based on the user's gender
            'U' - unspecified
            'M' - male
            'F' - female
            */
        switch (request.getGender()) {
            case 'M' -> {
                tempUser = new UserEntity().builder()
                        .username(request.getUsername())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .dateRegistered(new Timestamp(System.currentTimeMillis()))
                        .gender(request.getGender())
                        .userRating(0)
                        .deleted(false)
                        .honourableUser(false)
                        .isBanned(false)
                        .profilePicture(mediaRepository.findMediaEntityByMediaId(1))
                        .userRole(UserRoleEnum.REGULAR)
                        .build();
                userRepository.save(tempUser);
            }
            case 'F' -> {
                tempUser = new UserEntity().builder()
                        .username(request.getUsername())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .gender(request.getGender())
                        .userRating(0)
                        .honourableUser(false)
                        .dateRegistered(new Timestamp(System.currentTimeMillis()))
                        .isBanned(false)
                        .profilePicture(mediaRepository.findMediaEntityByMediaId(2))
                        .userRole(UserRoleEnum.REGULAR)
                        .deleted(false)
                        .build();
                userRepository.save(tempUser);
            }
            case 'U' -> {
                tempUser = new UserEntity().builder()
                        .username(request.getUsername())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .userRole(UserRoleEnum.REGULAR)
                        .gender(request.getGender())
                        .userRating(0)
                        .deleted(false)
                        .dateRegistered(new Timestamp(System.currentTimeMillis()))
                        .honourableUser(false)
                        .isBanned(false)
                        .profilePicture(mediaRepository.findMediaEntityByMediaId(3))
                        .build();
                userRepository.save(tempUser);
            }
            default -> throw new InvalidRequestFieldException();
        }
    }

    /**
     * @param userId
     * @return
     * @throws InvalidIdentificationException
     * @throws UnauthorizedDataAccessException
     * @Should return GetUserResponse_ when checks pass
     * @should throw InvalidIdentificationException _when id validation fails
     */
    @Override
    public GetUserResponse getUserById(int userId) throws InvalidIdentificationException, UnauthorizedDataAccessException {

        // validate if given ID belongs to an entity from DB
        //throws an 'InvalidIdentificationException'
        userValidator.validateId(userId);

        UserEntity returnedUser = userRepository.findByUserIdAndDeleted(userId, false);

        if (!Objects.equals(requestAccessToken.getAuthority(), "MOD") ||
                !Objects.equals(requestAccessToken.getAuthority(), "ADMIN")) {

            accessTokenAuthenticityUseCase.checkIfUserIdMatches(requestAccessToken, returnedUser);

        }

        return GetUserResponse.builder().user(UserConverter.convert(returnedUser)).build();

    }

    /**
     * @param username
     * @return
     * @throws InvalidIdentificationException
     * @throws UnauthorizedDataAccessException
     * @Should return GetUserResponse_ when checks pass
     * @should throw InvalidIdentificationException _when username validation fails
     * @should throw UnauthorizedDataAccessException_ when authoritity not mod or admin and request user username different from username
     */
    @Override
    public GetUserResponse getUserByUsername(@NotBlank String username) throws InvalidIdentificationException, UnauthorizedDataAccessException {

        userValidator.validateId(userRepository.findByUsernameAndDeleted(username, false).getUserId());

        UserEntity user = userRepository.findByUsernameAndDeleted(username, false);

        if (!Objects.equals(requestAccessToken.getAuthority(), "MOD") || !Objects.equals(requestAccessToken.getAuthority(), "ADMIN")) {

            accessTokenAuthenticityUseCase.checkIfUserIdMatches(requestAccessToken, user); // throws unauthorized data access exception

        }

        return GetUserResponse.builder().user(UserConverter.convert(user)).build();

    }

    /**
     * @param email
     * @return
     * @throws InvalidIdentificationException
     * @throws UnauthorizedDataAccessException
     * @Should return GetUserResponse_ when checks pass
     * @should throw InvalidIdentificationException _when email validation fails
     * @should throw UnauthorizedDataAccessException_ when authoritity not mod or admin and request user email different from email
     */
    @Override
    public GetUserResponse getUserByEmail(String email) {

        userValidator.validateEmail(userRepository.findUserByEmailAndDeleted(email, false).getEmail());

        UserEntity user = userRepository.findUserByEmailAndDeleted(email, false);

        if (!Objects.equals(requestAccessToken.getAuthority(), "MOD") || !Objects.equals(requestAccessToken.getAuthority(), "ADMIN")) {

            accessTokenAuthenticityUseCase.checkIfUserIdMatches(requestAccessToken, user);

        }

        return GetUserResponse.builder().user(UserConverter.convert(user)).build();
    }

    /**
     * @param userId
     * @return
     * @throws InvalidIdentificationException
     * @should return response object when checks pass
     * @should throw Invalid Identificatin exception when id validation fails
     */
    @Override
    public GetUserProfilePictureResponse getUserPFP(int userId) throws InvalidIdentificationException {

        userValidator.validateId(userId);

        String pfpUrl = userRepository.getUserProfilePicture(userId);

        return GetUserProfilePictureResponse.builder().pfpUrl(pfpUrl).build();

    }

    /**
     * @param userId
     * @should Set the user's honourable status as true when there are no active bans and the user's rating is above a certain threshold;
     * @should Set the user's honourable status as false when there is an active ban or the user's rating is below the threshold
     */
    @Transactional
    @Override
    public void evaluateUserHonourableStatus(int userId) {

        UserEntity user = userRepository.findByUserIdAndDeleted(userId, false);

        if (Boolean.TRUE.equals(!user.getHonourableUser() && !user.getIsBanned()) && user.getUserRating() >= 120) {
            userRepository.makeUserHonourable(userId);
        }

        if (user.getHonourableUser() && user.getIsBanned()) {
            userRepository.stripUserOfHonour(userId);
        }

        if (user.getHonourableUser() && user.getUserRating() < 120) {
            userRepository.stripUserOfHonour(userId);
        }

    }

    /**
     * @param userId
     * @should Set the user role to 'MOD'
     */
    @Transactional
    @Override
    public void makeUserAModerator(@NotNull int userId) throws UnauthorizedDataAccessException {

        userValidator.validateId(userId);

        if (Objects.equals(requestAccessToken.getAuthority(), "ADMIN")) {

            userRepository.makeUserModerator(userId);

        } else {

            throw new UnauthorizedDataAccessException("ADMINISTRATORS_ONLY");

        }

    }

    /**
     * @param userId
     * @should Set the user role to 'BANNED'
     */
    @Transactional
    @Override
    public void makeUserBanned(@NotNull int userId) {

        userValidator.validateId(userId);

        userRepository.setUserAsBanned(userId);

    }

    /**
     * @param userId
     * @should Set the user role to 'REGULAR'
     */
    @Transactional
    @Override
    public void makeUserUnbanned(int userId) {

        userValidator.validateId(userId);

        userRepository.setUserAsUnbanned(userId);

    }

    /**
     * @param userId
     * @should Set the user role to 'REGULAR'
     */
    @Override
    public void removeUserModPrivileges(int userId) throws UnauthorizedDataAccessException {

        userValidator.validateId(userId);

        if (Objects.equals(requestAccessToken.getAuthority(), "ADMIN")) {

            userRepository.stripModPrivilege(userId);

        } else {

            throw new UnauthorizedDataAccessException("ADMINISTRATORS_ONLY");

        }
    }

    /**
     * @param userId
     * @should call the delete method from the repository
     * @should throw InvalidIdentificatinException when id validation fails
     */
    @Override
    public void deleteUser(int userId) throws InvalidIdentificationException {

        userValidator.validateId(userId);

        UserEntity user = userRepository.findByUserIdAndDeleted(userId, false);

        if (!Objects.equals(requestAccessToken.getAuthority(), "MOD") || !Objects.equals(requestAccessToken.getAuthority(), "ADMIN")) {

            accessTokenAuthenticityUseCase.checkIfUserIdMatches(requestAccessToken, user);

        }

        userRepository.deleteByUserId(userId);

    }

    /**
     * @param userId
     * @should Separately calculate the sum of the ratings of posts and comments authored by the user;
     * @should Divide the sum of posts' ratings by 2, divide the sum of comments' ratings by 4;
     * @should Add up the result of the divisions(no remainders) and subtract with the number of bans the user has, multiplied by 50;
     * @should Set the user's rating as the end result of the equation
     */
    @Transactional
    @Override
    public void calculateUserRating(int userId) {

        userValidator.validateId(userId);

        int banCount = banRepository.countByUserId(userRepository.findByUserIdAndDeleted(userId, false));

        userRepository.updateUserRating(userId,
                (
                        (
                                (postRatingRepository.countByPostId_PostAuthor_UserIdAndWeightLessThan(userId, 0) * -1)
                                        + postRatingRepository.countByPostId_PostAuthor_UserIdAndWeightGreaterThan(userId, 0)
                        )
                                / 2
                                +
                                (
                                        (
                                                (commentRatingRepository.countByCommentId_CommentAuthor_UserIdAndWeightLessThan(userId, 0) * -1)
                                                        + commentRatingRepository.countByCommentId_CommentAuthor_UserIdAndWeightGreaterThan(userId, 0)
                                        )
                                                / 4
                                )
                )
                        - (banCount * 50)
        );
        evaluateUserHonourableStatus(userId);
    }

}
