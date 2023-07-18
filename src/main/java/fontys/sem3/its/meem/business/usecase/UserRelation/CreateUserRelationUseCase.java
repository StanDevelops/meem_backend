package fontys.sem3.its.meem.business.usecase.UserRelation;

import fontys.sem3.its.meem.business.exception.InvalidIdentificationException;
import fontys.sem3.its.meem.business.exception.UnauthorizedDataAccessException;
import fontys.sem3.its.meem.domain.model.FrontendUserRelation;
import fontys.sem3.its.meem.domain.request.UserRelation.CreateUserRelationRequest;

import javax.validation.constraints.NotNull;

public interface CreateUserRelationUseCase {
    /**
     * @param request
     * @return FrontEndUserRelation
     * @should create user relation and return the relation_ when checks pass
     * @should throw InvalidIdentificationException _when entity ID not found
     * @should throw UnauthorizedDataAccessException _when request is not from primary user
     */
    FrontendUserRelation createUserRelation(@NotNull CreateUserRelationRequest request) throws InvalidIdentificationException, UnauthorizedDataAccessException;
}

