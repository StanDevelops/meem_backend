package fontys.sem3.its.meem.persistence.repository;

import fontys.sem3.its.meem.domain.model.UserRelationTypeEnum;
import fontys.sem3.its.meem.persistence.entity.UserEntity;
import fontys.sem3.its.meem.persistence.entity.UserRelationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface UserRelationRepository extends JpaRepository<UserRelationEntity, Integer> {
    List<UserRelationEntity> findByRelationTypeAndSecondaryUser_UserId(UserRelationTypeEnum relationType, int userId);

    boolean existsByPrimaryUserAndSecondaryUserAndRelationType(UserEntity primaryUserId, UserEntity secondaryUserId, UserRelationTypeEnum relationType);

    boolean existsByPrimaryUserAndSecondaryUser(UserEntity primaryUserId, UserEntity secondaryUserId);

    @Query(value = " SELECT EXISTS(SELECT * from user_relations WHERE primary_user_id = ?1 and secondary_user_id = ?2)", nativeQuery = true)
    Boolean existsByPrimaryAndSecondaryUsers(int primaryUserId, int secondaryUserId);

    UserRelationEntity save(UserRelationEntity userRelationEntity);

    List<UserRelationEntity> findAllByPrimaryUserAndRelationType(UserEntity primaryUserId, UserRelationTypeEnum relationType);

    void deleteByPrimaryUserAndSecondaryUser(UserEntity primaryUserId, UserEntity secondaryUserId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE user_relations ur set ur.relation_type = ?1 where ur.primary_user_id = ?2 AND ur.secondary_user_id = ?3", nativeQuery = true)
    void changeUserRelation(@NotNull UserRelationTypeEnum relationType, int primaryUserId, @NotNull int secondaryUserId);

}
