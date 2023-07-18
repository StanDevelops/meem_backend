package fontys.sem3.its.meem.persistence.repository;

import fontys.sem3.its.meem.persistence.entity.CategoryEntity;
import fontys.sem3.its.meem.persistence.entity.PostEntity;
import fontys.sem3.its.meem.persistence.entity.SortingGroupEntity;
import fontys.sem3.its.meem.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Integer> {
    PostEntity save(@NotNull PostEntity postEntity);

    PostEntity findByPostUrl(String postUrl);

    List<PostEntity> findByGroupAndDeletedOrderByDatePostedDesc(SortingGroupEntity group, boolean deleted);

    List<PostEntity> findByGroupAndCategoryAndDeletedOrderByDatePostedDesc(SortingGroupEntity group, CategoryEntity category, Boolean deleted);

//    @Query("select p from PostEntity p where p.group = ?1 order by p.datePosted DESC")
//    List<PostEntity> getPostsBySortingGroupOrderedByDatePostedDesc(SortingGroupEntity group);

    List<PostEntity> findAllByGroup(@NotNull SortingGroupEntity groupId);

    List<PostEntity> findAllByPostAuthor(@NotNull UserEntity postAuthor);

    List<PostEntity> findAllByGroupAndCategory(@NotNull SortingGroupEntity groupId, @NotNull CategoryEntity categoryId);

    List<PostEntity> findAllByCategory(@NotNull CategoryEntity categoryId);

    PostEntity findByPostId(int postId);

    PostEntity findByPostIdAndDeleted(int postId, boolean deleted);

    void deletePostEntityByPostId(int postId);

    @Query(value = "select sum(weight) from PostRatingEntity where postId =: postId")
    int getSumOfPostRatings(@NotNull int postId);

    List<PostEntity> findAllByGroupAndDeleted(@NotNull SortingGroupEntity groupId, boolean deleted);

    @Query(value = "SELECT * from posts p where p.group_id = :group AND p.category_id = :category and p.deleted = false", nativeQuery = true)
    List<PostEntity> findByGroupAndCategoryAndDeletedFalse(int group, int category);

    List<PostEntity> findAllByGroupAndCategoryAndDeleted(@NotNull SortingGroupEntity groupId, @NotNull CategoryEntity categoryId, boolean deleted);

    List<PostEntity> findAllByCategoryAndDeleted(@NotNull CategoryEntity categoryId, boolean deleted);

    List<PostEntity> findAllByPostAuthorAndDeleted(@NotNull UserEntity postAuthor, boolean deleted);

    @Transactional
    boolean deleteByPostId(int postId);

    boolean existsByPostIdAndDeleted(int postId, boolean deleted);

    boolean existsByPostUrlAndDeleted(String postUrl, boolean deleted);


    @Transactional
    @Modifying
    @Query(value = "UPDATE PostEntity set postTitle = :newPostTitle where postId = :postId")
    void updatePostTitle(@Param("newPostTitle") @NotBlank String newPostTitle, @Param("postId") @NotNull int postId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE posts set group_id = :groupId where post_id = :postId and group_id <> :groupId", nativeQuery = true)
    void updatePostSortingGroup(@NotNull int groupId, @NotNull int postId);
//    @Transactional
//    @Modifying
//    @Query(value = " delete from funny.posts p  where p.post_id = ?1  order by post_id limit 1", nativeQuery = true)
//    void deletePost(@Param("postId") @NotNull int postId);
}
