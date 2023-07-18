package fontys.sem3.its.meem.persistence.repository;

import fontys.sem3.its.meem.persistence.entity.CommentEntity;
import fontys.sem3.its.meem.persistence.entity.CommentRatingEntity;
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
public interface CommentRatingRepository extends JpaRepository<CommentRatingEntity, Integer> {
    boolean existsByCommentIdAndUserId(CommentEntity commentId, UserEntity userId);

    int countByCommentId_CommentAuthor_UserIdAndWeightLessThan(int userId, int weight);

    int countByCommentId_CommentAuthor_UserIdAndWeightGreaterThan(int userId, int weight);

    CommentRatingEntity save(CommentRatingEntity commentRatingEntity);

    List<CommentRatingEntity> findAll();

    List<CommentRatingEntity> findAllByCommentId(int commentId);

    List<CommentRatingEntity> findAllByUserId(int userId);

    @Query(value = "SELECT * from Meem.comment_ratings cr where cr.comment_id = ?1 and cr.user_id = ?2", nativeQuery = true)
    CommentRatingEntity findByCommentIdAndUserId(int postId
            , int userId);

    //    Boolean existsByUserIdAndPostId(int userId, int commentId
    //    );
    Boolean existsByUserIdAndCommentIdAndWeight(UserEntity userId, CommentEntity commentId
            , int ratingWeight);

    Boolean deleteByCommentIdAndUserId(CommentEntity commentId
            , UserEntity userId);

    @Query(value = " SELECT EXISTS(SELECT * from comment_ratings WHERE comment_id = ?1 and user_id = ?2 and weight = ?3)", nativeQuery = true)
    Boolean existsByCommentAndUserAndWeight(@Param("commentId") int commentId
            , @Param("userId") int userId, @Param("ratingWeight") int ratingWeight);

    @Query(value = "SELECT * from comment_ratings where user_id = :userId and comment_id in (select c.comment_id from comments c where c.post_id = :postId)", nativeQuery = true)
    List<CommentRatingEntity> findAllCommentRatingsFromUserByPost(@NotNull int userId, @NotNull int postId);

    @Query(value = " SELECT EXISTS(SELECT * from comment_ratings WHERE comment_id = ?1 and user_id = ?2 )", nativeQuery = true)
    Boolean exists(@Param("commentId") int commentId
            , @Param("userId") int userId);

    @Transactional
    @Modifying
    @Query(value = "delete from Meem.comment_ratings where Meem.comment_ratings.user_id = ?1 AND Meem.comment_ratings.comment_id = ?2 order by comment_id limit 1", nativeQuery = true)
    void deleteCommentRating(@Param("userId") @NotNull int userId, @Param("commentId") @NotNull int commentId
    );

    @Transactional
    @Modifying
    @Query(value = "UPDATE comment_ratings cr set cr.weight = ?1 where cr.user_id = ?2 AND cr.comment_id = ?3", nativeQuery = true)
    void changeCommentRating(@Param("newRatingWeight") @NotNull int newRatingWeight, @Param("userId") int userId, @Param("commentId") @NotNull int commentId
    );

    @Query(value = "SELECT COUNT(weight) from comment_ratings where comment_id =?1 AND weight =?2", nativeQuery = true)
    int getCountOfCommentRatingsByCommentIdAndWeight(@Param("commentId") int commentId
            , @Param("ratingWeight") int ratingWeight);

//    @Query(value = "SELECT SUM(weight) from comment_ratings where comment_id in (select post_id from posts  where  group_id =?1) and weight = 1", nativeQuery = true)
//    int getSumOfPositiveCommentRatingsBySortingGroup(@Param("groupId") int groupId);
//
//    @Query(value = "SELECT SUM(weight) from post_ratings where post_id in (select post_id from posts  where  group_id =?1) and weight = -1", nativeQuery = true)
//    int getSumOfNegativePostRatingsBySortingGroup(@Param("groupId") int groupId);

//    @Query(value = "SELECT SUM(weight) from post_ratings where post_id in (select post_id from posts  where  group_id =?1)", nativeQuery = true)
//    int getSumOfPostRatingBySortingGroup(@Param("groupId") int groupId);
//
//    @Query(value = "SELECT SUM(weight) from post_ratings where post_id in (select post_id from posts  where  category_id =?1) and rating = 1", nativeQuery = true)
//    int getSumOfPositivePostRatingsByCategory(@Param("categoryId") int categoryId);

//    @Query(value = "SELECT SUM(weight) from post_ratings where post_id in (select post_id from posts  where  category_id =?1) and rating = -1", nativeQuery = true)
//    int getSumOfNegativePostRatingsByCategory(@Param("categoryId") int categoryId);
//
//    @Query(value = "SELECT SUM(weight) from post_ratings where post_id in (select post_id from posts  where  category_id =?1)", nativeQuery = true)
//    int getSumOfPostRatingByCategory(@Param("categoryId") int categoryId);

    @Query(value = "SELECT SUM(weight) from CommentRatingEntity where commentId = ?1")
    int getSumOfCommentRatingsById(@Param("commentId") int commentId
    );

//    @Query(value = "SELECT *  from post_ratings pr where pr.post_id in (select p.post_id from posts p where  p.group_id = ?1)", nativeQuery = true)
//    Optional<List<CommentRatingEntity>> getAllPostRatingsForSortingGroup(@Param("groupId") int groupId);
//
//    //
//    @Query(value = "SELECT * from post_ratings where post_id in (select p.post_id from posts p  where  category_id =?1)", nativeQuery = true)
//    Optional<List<CommentRatingEntity>> getPostRatingsForCategory(@Param("categoryId") int categoryId);
//
//    @Query(value = "SELECT * from post_ratings where post_id in (select p.post_id from posts p  where  category_id =?1 and group_id = ?2)", nativeQuery = true)
//    Optional<List<CommentRatingEntity>> getPostRatingsForCategoryAndSortingGroup(@Param("categoryId") int categoryId, @Param("groupId") int groupId);
//
//    @Query(value = "SELECT * from post_ratings where post_id in (select p.post_id from posts p  where  category_id =?2 and group_id = ?3) and user_id = ?1", nativeQuery = true)
//    Optional<List<CommentRatingEntity>> getPostRatingsFromUserByCategoryAndSortingGroup(@Param("userId") int userId, @Param("categoryId") int categoryId, @Param("groupId") int groupId);
//
//    @Query(value = "SELECT * from post_ratings where post_id in (select p.post_id from posts p  where  group_id = ?2) and user_id = ?1", nativeQuery = true)
//    Optional<List<CommentRatingEntity>> getPostRatingsFromUserBySortingGroup(@Param("userId") int userId, @Param("groupId") int groupId);
}
