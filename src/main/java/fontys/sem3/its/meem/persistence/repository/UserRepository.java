package fontys.sem3.its.meem.persistence.repository;

import fontys.sem3.its.meem.persistence.entity.UserEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;

//done
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity save(UserEntity userEntity);

    //    List<UserEntity> findAll();
    boolean existsByUserIdAndDeleted(int userId, boolean deleted);

    boolean existsUserEntityByEmailAndDeleted(String email, boolean deleted);

    boolean existsByUsernameAndDeleted(String username, boolean deleted);

//    @Query(value = "SELECT sum(weight) from post_ratings where post_id in (select p.post_id from posts p where p.post_author =: userId )", nativeQuery = true)
//    int postRatingsSumForAuthor(int userId);
//
//    @Query(value = "SELECT sum(weight) from comment_ratings where comment_id in (select c.comment_id from comments c where c.comment_author =: userId )", nativeQuery = true)
//    int commentRatingsSumForAuthor(int userId);

    //    @Transactional
    @Modifying
    @Query("update UserEntity u set u.username = ?1 where u.userId = ?2 ")
    int changeUsername(@NonNull String newUsername, int userId);

    //    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u set u.password = ?1 where u.userId = ?2 AND u.password = ?3")
    int changeUserPassword(@NotBlank String newPassword, int userId, @NotBlank String oldPassword);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity U SET U.userRating = :rating WHERE U.userId = :userId")
    void updateUserRating(int userId, int rating);



//    @Transactional
    @Modifying
    @Query("UPDATE UserEntity U SET U.honourableUser = true WHERE U.userId = ?1")
            int makeUserHonourable(int userId);

    //    @Transactional
    @Modifying
    @Query("UPDATE UserEntity U SET U.honourableUser = false WHERE U.userId = ?1")
    int stripUserOfHonour(int userId);

//    @Transactional
    @Modifying
    @Query("UPDATE UserEntity U SET U.userRole = 'MOD' WHERE U.userId = ?1")
            int makeUserModerator(int userId);

//    @Transactional
    @Modifying
    @Query("UPDATE UserEntity U SET U.userRole = 'REGULAR' WHERE U.userId = ?1")
            int stripModPrivilege(int userId);

//    @Transactional
    @Modifying
    @Query("UPDATE UserEntity U SET U.isBanned = true WHERE U.userId = ?1")
            int setUserAsBanned(int userId);

//    @Transactional
    @Modifying
    @Query("UPDATE UserEntity U SET U.isBanned = false WHERE U.userId = ?1")
    int setUserAsUnbanned(int userId);

    @Query(value = "Select media_address from media where media_id in( Select profile_picture from users u WHERE u.user_id = ?1)", nativeQuery = true)
    String getUserProfilePicture(int userId);

    //    @Transactional
    @Query(value = "SELECT * FROM users where email = ?1", nativeQuery = true)
    UserEntity findUserByEmailAndDeleted(@NotBlank String email, boolean deleted);

    UserEntity findByUserIdAndDeleted(int userId, boolean deleted);

    UserEntity findByUsernameAndDeleted(String username, boolean deleted);
//    UserEntity findByEmail(String email);


    //    @Transactional
    @Modifying
    int deleteByUserId(int userId);
//    void ban(int userId);
}
