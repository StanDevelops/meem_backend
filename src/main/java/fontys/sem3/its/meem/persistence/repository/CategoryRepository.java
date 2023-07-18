package fontys.sem3.its.meem.persistence.repository;

import fontys.sem3.its.meem.persistence.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//done
@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
  CategoryEntity save(CategoryEntity categoryEntity);

  List<CategoryEntity> findAll();

  CategoryEntity findByCategoryId(int categoryId);

  CategoryEntity findByCategoryName(String categoryName);

  int deleteByCategoryId(int categoryId);

  boolean existsByCategoryId(int categoryId);

  boolean existsByCategoryName(String categoryName);

  @Transactional
  @Modifying
  @Query(value = "UPDATE CategoryEntity cat set cat.categoryName = :newName where cat.categoryId = :categoryId")
  int updateCategoryName(@Param("categoryId") int categoryId, @Param("newName") String newName);

}
