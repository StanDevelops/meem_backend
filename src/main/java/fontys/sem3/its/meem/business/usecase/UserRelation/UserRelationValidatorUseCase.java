package fontys.sem3.its.meem.business.usecase.UserRelation;

import javax.validation.constraints.NotNull;

public interface UserRelationValidatorUseCase {
    /**
     * @param primaryUserId
     * @param secondaryUserId
     * @return true
     * @should return true _when relation with given ids exists
     * @should return false _when relation with given ids does not exist
     */
    boolean validateId(@NotNull int primaryUserId, @NotNull int secondaryUserId);
}
