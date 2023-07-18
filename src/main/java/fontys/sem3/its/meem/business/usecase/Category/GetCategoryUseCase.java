package fontys.sem3.its.meem.business.usecase.Category;

import fontys.sem3.its.meem.domain.response.Category.GetCategoryResponse;

public interface GetCategoryUseCase {
//    GetCategoryResponse getCategoryById(int categoryId);
    GetCategoryResponse getCategoryByName(String categoryName);
//    CategoryEntity getCategoryEntityByName(String categoryName);
}

