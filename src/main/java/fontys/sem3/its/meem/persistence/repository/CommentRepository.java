package fontys.sem3.its.meem.persistence.repository;

import fontys.sem3.its.meem.persistence.entity.CommentEntity;
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
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    @Query("select c from CommentEntity c where (c.post = :postId and c.deleted = false) or " +
            "(c.post = :postId and c.deleted = true and c.commentId in(select cc from CommentEntity cc where cc.parentCommentId = c.commentId))")
    List<CommentEntity> getAllCommentsOnPost(@Param("postId") int postId);

    CommentEntity findByCommentAuthorAndDeleted(UserEntity commentAuthor, boolean isDeleted);

    CommentEntity save(@NotNull CommentEntity commentEntity);

    CommentEntity findByCommentIdAndDeleted(@NotNull int commentId, boolean deleted);

//    List<CommentEntity> findAllByPostId(@NotNull PostEntity postId);

    //    @Query(value = "select CommentEntity  from CommentEntity where post_id =: postId")
//    List<CommentEntity> getAllCommentsOnPost(@NotNull int postId);
//    List<CommentEntity> findAllByCommentAuthor(@NotNull UserEntity commentAuthor);

    boolean existsByCommentId(@NotNull int commentId);

    @Transactional
    @Modifying
    @Query(value = "update CommentEntity set commentBody = :newBody where commentId = :commentId")
    void updateCommentBody(@NotNull int commentId, @NotBlank String newBody);
}
