package fontys.sem3.its.meem.persistence.repository;

import fontys.sem3.its.meem.persistence.entity.MediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface MediaRepository extends JpaRepository<MediaEntity, Integer> {
    boolean existsByMediaAddress(String mediaAddress);

    MediaEntity findMediaEntityByMediaId(@NonNull int mediaId);

    MediaEntity save(MediaEntity mediaEntity);

    @Transactional
    @Modifying
//    @Query(value = "UPDATE Commandments c SET c.commandment_rule = ?1 WHERE c.commandment_nr = ?2", nativeQuery = true)
    @Query(value = "UPDATE MediaEntity set mediaAddress = ?1 where mediaId = ?2")
    void changeAddress(String newAddress, int mediaId);
}
