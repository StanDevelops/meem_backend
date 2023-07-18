package fontys.sem3.its.meem.business.validator;

import fontys.sem3.its.meem.business.exception.NonExistingRatingException;
import fontys.sem3.its.meem.business.usecase.PostRating.PostRatingValidatorUseCase;
import fontys.sem3.its.meem.persistence.repository.PostRatingRepository;
import fontys.sem3.its.meem.persistence.repository.PostRepository;
import fontys.sem3.its.meem.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@AllArgsConstructor
public class PostRatingValidator implements PostRatingValidatorUseCase {
    private final PostRatingRepository postRatingRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    /**
     * @param userId
     * @param postId
     * @return true
     * @should return true _when rating with given id exists
     * @should throw NonExistingRatingException _when rating with given id does not exist
     */
    @Override
    public boolean validateId(@NotNull int userId, @NotNull int postId) throws NonExistingRatingException {
        if (!postRatingRepository.existsByPostIdAndUserId(
                postRepository.findByPostIdAndDeleted(postId, false), userRepository.findByUserIdAndDeleted(userId, false))) { //check if entity ID is null or not belonging to DB entity
            throw new NonExistingRatingException(); //NO MATCH! NOT GOOD! Throw a hissy!
        }
        return true;
    }

    /**
     * @param ratingId
     * @return
     * @throws NonExistingRatingException
     * @should return true _when rating with given id exists
     * @should throw NonExistingRatingException _when rating with given id does not exist
     */
    @Override
    public boolean validateId(int ratingId) throws NonExistingRatingException {
        if (!postRatingRepository.existsByRatingId(ratingId)) {
            throw new NonExistingRatingException(); //NO MATCH! NOT GOOD! Throw a hissy!
        }
        return true;
    }
}
