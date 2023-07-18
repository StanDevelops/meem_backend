package fontys.sem3.its.meem.business.service;

import fontys.sem3.its.meem.business.exception.ExistingRelationException;
import fontys.sem3.its.meem.business.exception.InvalidIdentificationException;
import fontys.sem3.its.meem.business.exception.InvalidRequestFieldException;
import fontys.sem3.its.meem.business.exception.UnauthorizedDataAccessException;
import fontys.sem3.its.meem.business.service.converter.UserRelationConverter;
import fontys.sem3.its.meem.business.usecase.AccessToken.AccessTokenAuthenticityUseCase;
import fontys.sem3.its.meem.business.usecase.ActivityHistory.CreateActivityHistoryUseCase;
import fontys.sem3.its.meem.business.usecase.User.UserValidatorUseCase;
import fontys.sem3.its.meem.business.usecase.UserRelation.*;
import fontys.sem3.its.meem.domain.model.AccessToken;
import fontys.sem3.its.meem.domain.model.FrontendUserRelation;
import fontys.sem3.its.meem.domain.model.UserRelationTypeEnum;
import fontys.sem3.its.meem.domain.request.UserRelation.ChangeUserRelationRequest;
import fontys.sem3.its.meem.domain.request.UserRelation.CreateUserRelationRequest;
import fontys.sem3.its.meem.persistence.entity.UserEntity;
import fontys.sem3.its.meem.persistence.entity.UserRelationEntity;
import fontys.sem3.its.meem.persistence.repository.UserRelationRepository;
import fontys.sem3.its.meem.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserRelationServiceImpl implements ChangeUserRelationUseCase, CreateUserRelationUseCase, DeleteUserRelationUseCase,
        GetUserRelationsUseCase, GetUserRelationUseCase {
    private final UserRepository userRepository;
    private final UserValidatorUseCase userValidator;
    private final UserRelationRepository userRelationRepository;
    private final UserRelationValidatorUseCase userRelationValidator;
    private final CreateActivityHistoryUseCase createActivityHistoryUseCase;
    private final AccessTokenAuthenticityUseCase accessTokenAuthenticityUseCase;
    private AccessToken requestAccessToken;


    /**
     * @param request
     * @return FrontEndUserRelation
     * @should change user relation type and return the changed relation_ when checks pass
     * @should throw InvalidIdentificationException _when entity ID not found
     * @should throw UnauthorizedDataAccessException _when request is not from primary user
     */
    @Override
    public FrontendUserRelation changeUserRelation(ChangeUserRelationRequest request) throws InvalidIdentificationException, UnauthorizedDataAccessException {

        return null;
    }

    /**
     * @param request
     * @return FrontEndUserRelation
     * @should create user relation and return the relation_ when checks pass
     * @should throw InvalidIdentificationException _when primary user entity ID not found
     * @should throw InvalidIdentificationException _when secondary user entity ID not found
     * @should throw UnauthorizedDataAccessException _when request is not from primary user
     * @should throw InvalidRequestFieldException _when unrecognised relation type detected
     * @should throw ExistingRelationException _when user already has relation with second user
     */
    @Override
    public FrontendUserRelation createUserRelation(CreateUserRelationRequest request) throws InvalidIdentificationException, UnauthorizedDataAccessException, ExistingRelationException {
        userValidator.validateId(request.getPrimaryUserId());
        userValidator.validateId(request.getSecondaryUserId());
        try {
            UserRelationTypeEnum.valueOf(request.getRelationType());
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestFieldException();
        }
        if (userRelationRepository.existsByPrimaryUserAndSecondaryUser(userRepository.findByUserIdAndDeleted(request.getPrimaryUserId(), false), userRepository.findByUserIdAndDeleted(request.getSecondaryUserId(), false))) {
            throw new ExistingRelationException();
        }

        return UserRelationConverter.convert(userRelationRepository.save(UserRelationEntity.builder()
                .primaryUser(userRepository.findByUserIdAndDeleted(request.getPrimaryUserId(), false))
                .secondaryUser(userRepository.findByUserIdAndDeleted(request.getSecondaryUserId(), false))
                .relationType(UserRelationTypeEnum.valueOf(request.getRelationType()))

                .build()));
    }

    /**
     * @param primaryUserUsername
     * @param secondaryUserUsername
     * @param relationType
     * @should delete user relation _ when checks pass
     * @should throw InvalidIdentificationException _when entity ID not found
     * @should throw UnauthorizedDataAccessException _when request is not from primary user
     */
    @Override
    public void deleteUserRelation(String primaryUserUsername, String secondaryUserUsername, String relationType) throws InvalidIdentificationException, UnauthorizedDataAccessException {

    }

    /**
     * @param primaryUserUsername
     * @param secondaryUserUsername
     * @return FrontEndUserRelation
     * @should return the relation status  with a user_ when checks pass
     * @should throw InvalidIdentificationException _when entity ID not found
     * @should throw UnauthorizedDataAccessException _when request is not from primary user

     */
    @Override
    public FrontendUserRelation getUserRelationStatus(String primaryUserUsername, String secondaryUserUsername) throws UnauthorizedDataAccessException {
        return null;
    }

    /**
     * @param primaryUserUsername
     * @param relationType
     * @return List of FrontEndUserRelation
     * @should return the list of user relations_ when checks pass and records exist
     * @should return empty list_ when checks pass and no records exist
     * @should throw InvalidIdentificationException _when entity ID not found
     * @should throw UnauthorizedDataAccessException _when request is not from primary user
     * @should throw InvalidRequestFieldException _when unrecognised relation type detected
     */
    @Override
    public List<FrontendUserRelation> getUserRelationStatusByType(String primaryUserUsername, String relationType) throws InvalidIdentificationException, UnauthorizedDataAccessException {
        userValidator.validateUsername(primaryUserUsername); //throws InvalidIdentificationException
        try {
            UserRelationTypeEnum.valueOf(relationType);
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestFieldException();
        }
        UserEntity user = userRepository.findByUsernameAndDeleted(primaryUserUsername, false);
        accessTokenAuthenticityUseCase.checkIfUserIdMatches(requestAccessToken, user); //throws UnauthorizedDataAccessException
        return !userRelationRepository.findAllByPrimaryUserAndRelationType(user,
                UserRelationTypeEnum.FOLLOWED).isEmpty() ? userRelationRepository.findAllByPrimaryUserAndRelationType(user,
                UserRelationTypeEnum.FOLLOWED).stream().map(UserRelationConverter::convert).toList() : new ArrayList<>();
    }

    /**
     * @param userId
     * @return
     * @throws InvalidIdentificationException
     * @should return list of user entities that follow the given user_ when checks pass and repo not empty
     * @should throw InvalidIdentificationException_ when user id validation fails
     * @should return empty list when repo empty
     */
    @Override
    public List<UserRelationEntity> getUserFollowers(int userId) throws InvalidIdentificationException {
        userValidator.validateId(userId); // throws InvalidIdentificatinException
        return userRelationRepository.findByRelationTypeAndSecondaryUser_UserId(UserRelationTypeEnum.FOLLOWED, userId).isEmpty() ? new ArrayList<>() :
                userRelationRepository.findByRelationTypeAndSecondaryUser_UserId(UserRelationTypeEnum.FOLLOWED, userId);
    }

}
