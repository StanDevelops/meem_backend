package fontys.sem3.its.meem.business.validator;

import fontys.sem3.its.meem.business.exception.InvalidIdentificationException;
import fontys.sem3.its.meem.business.usecase.Category.CategoryValidatorUseCase;
import fontys.sem3.its.meem.persistence.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@AllArgsConstructor
public class CategoryValidator implements CategoryValidatorUseCase {
    private final CategoryRepository categoryRepository;

    @Override
    public Boolean validateId(@NotNull int categoryId) {
        //check if entity ID is null or not belonging to DB entity
        if (categoryId == 0 || !categoryRepository.existsByCategoryId(categoryId)) {  //check if entity ID is null or not belonging to DB entity
            throw new InvalidIdentificationException(); //NO MATCH! NOT GOOD! Throw a hissy!
        }
        return true;
    }
}
