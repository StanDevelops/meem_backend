package fontys.sem3.its.meem.business.usecase.UserRelation;

import fontys.sem3.its.meem.business.exception.UnauthorizedDataAccessException;
import fontys.sem3.its.meem.domain.model.FrontendUserRelation;

import javax.validation.constraints.NotBlank;

public interface GetUserRelationUseCase {
    /**
     * @param request
     * @return FrontEndUserRelation
     * @should return the relation status  with a user_ when checks pass
     * @should throw InvalidIdentificationException _when entity ID not found
     * @should throw UnauthorizedDataAccessException _when request is not from primary user
     */
    FrontendUserRelation getUserRelationStatus(@NotBlank String primaryUserUsername, @NotBlank String secondaryUserUsername)
            throws UnauthorizedDataAccessException;
}

