package fontys.sem3.its.meem.business.validator;

import fontys.sem3.its.meem.business.usecase.UserRelation.UserRelationValidatorUseCase;
import fontys.sem3.its.meem.persistence.repository.UserRelationRepository;
import fontys.sem3.its.meem.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@AllArgsConstructor
public class UserRelationValidator implements UserRelationValidatorUseCase {
    private final UserRelationRepository userRelationRepository;
    private final UserRepository userRepository;

    /**
     * @param primaryUserId
     * @param secondaryUserId
     * @return true
     * @should return true _when relation with given id exists
     * @should return false_when relation with given id does not exist
     */
    @Override
    public boolean validateId(@NotNull int primaryUserId, @NotNull int secondaryUserId) {
        //check if entity ID is null or not belonging to DB entity
        return userRelationRepository.existsByPrimaryUserAndSecondaryUser(userRepository.findByUserIdAndDeleted(primaryUserId, false),
                userRepository.findByUserIdAndDeleted(secondaryUserId, false));
    }
}
