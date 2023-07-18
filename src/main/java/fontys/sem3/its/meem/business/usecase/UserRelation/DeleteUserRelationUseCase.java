package fontys.sem3.its.meem.business.usecase.UserRelation;

import fontys.sem3.its.meem.business.exception.InvalidIdentificationException;
import fontys.sem3.its.meem.business.exception.UnauthorizedDataAccessException;

import javax.validation.constraints.NotBlank;

public interface DeleteUserRelationUseCase {
    /**
     * @param request
     * @should delete user relation _ when checks pass
     * @should throw InvalidIdentificationException _when entity ID not found
     * @should throw UnauthorizedDataAccessException _when request is not from primary user
     */
    void deleteUserRelation(@NotBlank String primaryUserUsername, @NotBlank String secondaryUserUsername, @NotBlank String relationType) throws InvalidIdentificationException, UnauthorizedDataAccessException;
}

