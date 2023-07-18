package fontys.sem3.its.meem.persistence.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepositoryTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void updateCategoryName() {
    }

//    @Test
//    void save_shouldSaveCategoryWithAllFields() {
//        CategoryEntity newCategory = CategoryEntity.builder().categoryName("All-time").build();
//
//        CategoryEntity savedCategory = categoryRepository.save(newCategory);
//        assertNotNull(savedCategory);
//
//        savedCategory = entityManager.find(CategoryEntity.class, savedCategory.getCategoryId());
//
//        CategoryEntity expectedCategory = CategoryEntity.builder().categoryId(1).categoryName("All-time").build();
//
//        assertEquals(expectedCategory, savedCategory);
//    }

//    private CategoryEntity saveCategory(String categoryName) {
//        CategoryEntity category = CategoryEntity.builder().categoryName(categoryName).build();
//        entityManager.persist(category);
//        return category;
//    }
}