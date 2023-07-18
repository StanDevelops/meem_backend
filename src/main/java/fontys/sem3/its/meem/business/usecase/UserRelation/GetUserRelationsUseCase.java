package fontys.sem3.its.meem.business.usecase.UserRelation;

import fontys.sem3.its.meem.business.exception.InvalidIdentificationException;
import fontys.sem3.its.meem.business.exception.UnauthorizedDataAccessException;
import fontys.sem3.its.meem.domain.model.FrontendUserRelation;
import fontys.sem3.its.meem.persistence.entity.UserRelationEntity;

import javax.validation.constraints.NotBlank;
import java.util.List;

public interface GetUserRelationsUseCase {
    /**
     * @param request
     * @return FrontEndUserRelation
     * @should return the relation status  with a user_ when checks pass
     * @should throw InvalidIdentificationException _when entity ID not found
     * @should throw UnauthorizedDataAccessException _when request is not from primary user
     * @should throw InvalidRequestFieldException _when unrecognised relation type detected
     */
    List<FrontendUserRelation> getUserRelationStatusByType(@NotBlank String primaryUserUsername, @NotBlank String relationType)
            throws InvalidIdentificationException, UnauthorizedDataAccessException;

    /**
     * @param userId
     * @return
     * @throws InvalidIdentificationException
     * @should return list of user entities that follow the given user_ when checks pass and repo not empty
     * @should throw InvalidIdentificationException_ when user id validation fails
     * @should return empty list when repo empty
     */
    List<UserRelationEntity> getUserFollowers(int userId) throws InvalidIdentificationException;
}

