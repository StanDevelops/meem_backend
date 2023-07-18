package fontys.sem3.its.meem.business.validator;

import fontys.sem3.its.meem.business.exception.InvalidIdentificationException;
import fontys.sem3.its.meem.business.exception.InvalidUrlException;
import fontys.sem3.its.meem.business.usecase.Post.PostValidatorUseCase;
import fontys.sem3.its.meem.persistence.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Component
@AllArgsConstructor
public class PostValidator implements PostValidatorUseCase {
    private final PostRepository postRepository;

    @Override
    public void validateId(int postId) {
        if (postId == 0 || !postRepository.existsByPostIdAndDeleted(postId, false)) { //check if entity ID is null or not belonging to DB entity
            throw new InvalidIdentificationException(); //NO MATCH! NOT GOOD! Throw a hissy!
        }
    }

    public void validateUrl(@NotBlank String postUrl) {
        if (!postRepository.existsByPostUrlAndDeleted(postUrl, false)) { //check if entity ID is null or not belonging to DB entity
            throw new InvalidUrlException(); //NO MATCH! NOT GOOD! Throw a hissy!
        }
    }
}
