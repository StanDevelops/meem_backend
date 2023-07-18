package fontys.sem3.its.meem.business.usecase.UserRelation;

import fontys.sem3.its.meem.business.exception.InvalidIdentificationException;
import fontys.sem3.its.meem.business.exception.UnauthorizedDataAccessException;
import fontys.sem3.its.meem.domain.model.FrontendUserRelation;
import fontys.sem3.its.meem.domain.request.UserRelation.ChangeUserRelationRequest;

import javax.validation.constraints.NotNull;

public interface ChangeUserRelationUseCase {
    /**
     * @param request
     * @return FrontEndUserRelation
     * @should change user relation type and return the changed relation_ when checks pass
     * @should throw InvalidIdentificationException _when entity ID not found
     * @should throw UnauthorizedDataAccessException _when request is not from primary user
     */
    FrontendUserRelation changeUserRelation(@NotNull ChangeUserRelationRequest request)
            throws InvalidIdentificationException, UnauthorizedDataAccessException;
}

