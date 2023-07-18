package fontys.sem3.its.meem.persistence.repository;

import fontys.sem3.its.meem.persistence.entity.BanEntity;
import fontys.sem3.its.meem.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

@Repository
public interface BanRepository extends JpaRepository<BanEntity, Integer> {
    boolean existsByBanId(int banId);

    @Query("select count(b) from BanEntity b where b.userId = ?1")
    long countNumberOfBans(UserEntity userId);

    int countByUserId(UserEntity userId);

    BanEntity save(BanEntity banEntity);

    @Query(value = "select * from bans where user_id =: userId and expired = false", nativeQuery = true)
    BanEntity getNonExpiredBan(@NotNull int userId);

    @Query(value = "select if(expiration_date <= current_date, true, false) from bans where ban_id =: banId", nativeQuery = true)
    Boolean checkIfBanHasExpired(int banId);

    @Transactional
    @Modifying
    @Query(value = "update BanEntity set expired = true where banId =: banId")
    void setBanAsExpired(int banId);
}
