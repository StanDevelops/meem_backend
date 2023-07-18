package fontys.sem3.its.meem.persistence.repository;

import fontys.sem3.its.meem.persistence.entity.CommandmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CommandmentRepository extends JpaRepository<CommandmentEntity, Integer> {
    boolean existsByCommandmentNr(int commandmentNr);

    CommandmentEntity save(CommandmentEntity commandmentEntity);

    CommandmentEntity findByCommandmentNr(int commandmentNr);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Commandments c SET c.commandment_rule = ?1 WHERE c.commandment_nr = ?2", nativeQuery = true)
    void changeCommandment(String newCommandment, int commandmentNr);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Commandments c SET c.punishment_duration = ?1 WHERE c.commandment_nr = ?2", nativeQuery = true)
    void changePunishmentDuration(String newDuration, int commandmentNr);

}
