package fontys.sem3.its.meem.persistence.repository;

import fontys.sem3.its.meem.persistence.entity.SortingGroupEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

//done
public interface SortingGroupRepository extends JpaRepository<SortingGroupEntity, Long> {
    SortingGroupEntity save(SortingGroupEntity sortingGroupEntity);

    //    String getSortingGroupEntityByGroupId
    List<SortingGroupEntity> findByOrderByGroupIdAsc();

    List<SortingGroupEntity> findByGroupNameNotNullOrderByGroupIdAsc();


//    List<SortingGroupEntity> findAllOrderByGroupIdAsc();

    boolean existsByGroupId(int sortingGroupId);

    boolean existsByGroupName(String sortingGroupName);

    SortingGroupEntity findByGroupId(int groupId);

    SortingGroupEntity findByGroupName(String groupName);

    SortingGroupEntity findByGroupRank(int groupRank);

    @Transactional
    @Modifying
    @Query("update SortingGroupEntity s set s.groupName = ?1 where s.groupId = ?2 ")
    int updateGroupName(@NonNull String newGroupName, int groupId);

    @Query(value = "SELECT max(groupRank) from SortingGroupEntity ")
    int getMaxRank();

    @Query(value = "SELECT min(groupRank) from SortingGroupEntity ")
    int getMinRank();

    boolean deleteByGroupId(int sortingGroupId);

}
