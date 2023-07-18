package fontys.sem3.its.meem.business.usecase.Category;

import fontys.sem3.its.meem.domain.request.Category.CreateCategoryRequest;
import fontys.sem3.its.meem.persistence.entity.CategoryEntity;

public interface CreateCategoryUseCase {
    CategoryEntity createCategory(CreateCategoryRequest request);
}
