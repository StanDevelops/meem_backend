package fontys.sem3.its.meem.business.validator;

import fontys.sem3.its.meem.business.exception.InvalidIdentificationException;
import fontys.sem3.its.meem.business.usecase.User.UserValidatorUseCase;
import fontys.sem3.its.meem.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Component
@AllArgsConstructor
public class UserValidator implements UserValidatorUseCase {
    private final UserRepository userRepository;

    @Override
    public boolean validateId(int userId) {
//check if entity ID is null or not belonging to DB entity
        if (userId == 0 || !userRepository.existsByUserIdAndDeleted(userId, false)) {  //check if entity ID is null or not belonging to DB entity
            throw new InvalidIdentificationException(); //NO MATCH! NOT GOOD! Throw a hissy!
        }

        return true;

    }

    /**
     * @param email
     * @throws InvalidIdentificationException
     */
    @Override
    public boolean validateEmail(String email) throws InvalidIdentificationException {

        if (!userRepository.existsUserEntityByEmailAndDeleted(email, false)) {  //check if entity ID is null or not belonging to DB entity
            throw new InvalidIdentificationException(); //NO MATCH! NOT GOOD! Throw a hissy!
        }

        return true;

    }

    /**
     * @param username
     * @return
     * @throws InvalidIdentificationException
     */
    @Override
    public boolean validateUsername(@NotBlank String username) throws InvalidIdentificationException {
        if (userRepository.existsByUsernameAndDeleted(username, false)) {
            return true;
        } else {
            throw new InvalidIdentificationException();
        }
    }

}
