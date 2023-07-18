package fontys.sem3.its.meem.business.service.converter;

import fontys.sem3.its.meem.domain.model.Category;
import fontys.sem3.its.meem.persistence.entity.CategoryEntity;

public class CategoryConverter {
    public static Category convert(CategoryEntity categoryEntity) {
        return Category.builder()
                .categoryId(categoryEntity.getCategoryId())
                .categoryName(categoryEntity.getCategoryName())
                .build();
    }

    //done
}
