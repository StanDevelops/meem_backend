package fontys.sem3.its.meem.persistence.repository;

import fontys.sem3.its.meem.persistence.entity.PostEntity;
import fontys.sem3.its.meem.persistence.entity.PostRatingEntity;
import fontys.sem3.its.meem.persistence.entity.SortingGroupEntity;
import fontys.sem3.its.meem.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface PostRatingRepository extends JpaRepository<PostRatingEntity, Integer> {
    boolean existsByPostIdAndUserId(PostEntity postId, UserEntity userId);

    @Query(value = "select avg(pr.weight) from PostRatingEntity pr where pr.postId.group = :group")
    double getAverageRatingForSortingGroup(SortingGroupEntity group);

    long countByPostId_GroupAndWeightGreaterThan(SortingGroupEntity group, int weight);

    long countByPostId_GroupAndWeightLessThan(SortingGroupEntity group, int weight);

    boolean existsByRatingId(int ratingId);

    long countByPostId_Group(SortingGroupEntity group);

    PostRatingEntity save(PostRatingEntity postRatingEntity);

    List<PostRatingEntity> findAll();

    List<PostRatingEntity> findAllByPostId(int postId);

    List<PostRatingEntity> findAllByUserId(int userId);

    PostRatingEntity findByPostIdAndUserId(PostEntity postId, UserEntity userId);

//    @Query(value = "SELECT sum(weight) from post_ratings where post_id in (select p.post_id from posts p where p.post_author = :userId )", nativeQuery = true)
//    int postRatingsSumForAuthor(int userId);

//    @Query(value = "SELECT * from Meem.post_ratings pr where pr.post_id = ?1 and user_id = ?2", nativeQuery = true)
//    PostRatingEntity findByPostIdAndUserId(int postId, int userId);

    //    Boolean existsByUserIdAndPostId(int userId, int postId);
    Boolean existsByUserIdAndPostIdAndWeight(UserEntity userId, PostEntity postId, int ratingWeight);

    void deleteByPostIdAndUserId(PostEntity postId, UserEntity userId);


    @Query(value = " SELECT EXISTS(SELECT * from post_ratings WHERE post_id = ?1 and user_id = ?2 and weight = ?3)", nativeQuery = true)
    Boolean existsByPostAndUserAndWeight(@Param("postId") int postId, @Param("userId") int userId, @Param("ratingWeight") int ratingWeight);

    @Query(value = " SELECT EXISTS(SELECT * from post_ratings WHERE post_id = ?1 and user_id = ?2 )", nativeQuery = true)
    Boolean exists(@Param("postId") int postId, @Param("userId") int userId);

    @Transactional
    @Modifying
    @Query(value = "delete from Meem.post_ratings where Meem.post_ratings.rating_id = ?1 order by rating_id limit 1", nativeQuery = true)
    void deletePostRating(@Param("ratingId") @NotNull int ratingId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE post_ratings pr set pr.weight = ?1 where pr.rating_id = ?2", nativeQuery = true)
    void changePostRating(@Param("newRatingWeight") @NotNull int newRatingWeight, @Param("ratingId") int ratingId);

    @Query(value = "SELECT COUNT(weight) from post_ratings where post_id =?1 AND weight =?2", nativeQuery = true)
    int getCountOfPostRatingsByPostIdAndWeight(@Param("postId") int postId, @Param("ratingWeight") int ratingWeight);

    @Query(value = "SELECT SUM(weight) from post_ratings where post_id in (select post_id from posts  where  group_id =?1) and weight = 1", nativeQuery = true)
    int getSumOfPositivePostRatingsBySortingGroup(@Param("groupId") int groupId);

    @Query(value = "SELECT SUM(weight) from post_ratings where post_id in (select post_id from posts  where  group_id =?1) and weight = -1", nativeQuery = true)
    int getSumOfNegativePostRatingsBySortingGroup(@Param("groupId") int groupId);

    @Query(value = "SELECT SUM(weight) from post_ratings where post_id in (select post_id from posts  where  group_id =?1)", nativeQuery = true)
    int getSumOfPostRatingBySortingGroup(@Param("groupId") int groupId);

    @Query(value = "SELECT SUM(weight) from post_ratings where post_id in (select post_id from posts  where  category_id =?1) and weight = 1", nativeQuery = true)
    int getSumOfPositivePostRatingsByCategory(@Param("categoryId") int categoryId);

    @Query(value = "SELECT SUM(weight) from post_ratings where post_id in (select post_id from posts  where  category_id =?1) and weight = -1", nativeQuery = true)
    int getSumOfNegativePostRatingsByCategory(@Param("categoryId") int categoryId);

    @Query(value = "SELECT SUM(weight) from Meem.post_ratings where post_id in (select post_id from posts  where  category_id =?1)", nativeQuery = true)
    int getSumOfPostRatingByCategory(@Param("categoryId") int categoryId);

    @Query(value = "SELECT SUM(weight) from post_ratings where post_id =:postId", nativeQuery = true)
    int getSumOfPostRatingsById(final int postId);

    @Query("select count(p) from PostRatingEntity p where p.postId.postAuthor.userId = ?1 and p.weight < ?2")
    int countByPostId_PostAuthor_UserIdAndWeightLessThan(int userId, int weight);

    @Query("select count(p) from PostRatingEntity p where p.postId.postAuthor.userId = ?1 and p.weight > ?2")
    int countByPostId_PostAuthor_UserIdAndWeightGreaterThan(int userId, int weight);


//    @Query("select count(p) from PostRatingEntity p where p.postId = ?1 and p.weight < ?2")
//    int countByPostIdAndWeightLessThan(PostEntity postId, int weight);
//
//    @Query("select count(p) from PostRatingEntity p where p.postId = ?1 and p.weight > ?2")
//    int countByPostIdAndWeightGreaterThan(PostEntity postId, int weight);

    @Query(value = "SELECT *  from post_ratings pr where pr.post_id in (select p.post_id from posts p where  p.group_id = ?1)", nativeQuery = true)
    List<PostRatingEntity> getAllPostRatingsForSortingGroup(@Param("groupId") int groupId);

    //
    @Query(value = "SELECT * from post_ratings where post_id in (select p.post_id from posts p  where  category_id =?1)", nativeQuery = true)
    List<PostRatingEntity> getPostRatingsForCategory(@Param("categoryId") int categoryId);

    @Query(value = "SELECT * from post_ratings where post_id in (select p.post_id from posts p  where  category_id =?1 and group_id = ?2)", nativeQuery = true)
    List<PostRatingEntity> getPostRatingsForCategoryAndSortingGroup(@Param("categoryId") int categoryId, @Param("groupId") int groupId);

    @Query(value = "SELECT * from post_ratings where post_id in (select p.post_id from posts p  where  category_id =?2 and group_id = ?3) and user_id = ?1", nativeQuery = true)
    List<PostRatingEntity> getPostRatingsFromUserByCategoryAndSortingGroup(@Param("userId") int userId, @Param("categoryId") int categoryId, @Param("groupId") int groupId);

    @Query(value = "SELECT * from post_ratings where post_id in (select p.post_id from posts p  where  group_id = ?2) and user_id = ?1", nativeQuery = true)
    List<PostRatingEntity> getPostRatingsFromUserBySortingGroup(@Param("userId") int userId, @Param("groupId") int groupId);
}
//List<PostRatingEntity> findAllByPostIdIn