package fontys.sem3.its.meem.persistence.repository;

import fontys.sem3.its.meem.domain.model.ActivityTypeEnum;
import fontys.sem3.its.meem.persistence.entity.ActivityHistoryEntity;
import fontys.sem3.its.meem.persistence.entity.CommentEntity;
import fontys.sem3.its.meem.persistence.entity.PostEntity;
import fontys.sem3.its.meem.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityHistoryRepository extends JpaRepository<ActivityHistoryEntity, Integer> {
    boolean existsByActivityId(int activity);

    ActivityHistoryEntity save(ActivityHistoryEntity activityHistoryEntity);

    ActivityHistoryEntity findByActivityId(int activity);

    List<ActivityHistoryEntity> findAllByPrimaryUser(UserEntity user);

    List<ActivityHistoryEntity> findAllByActivityType(ActivityTypeEnum activityType);

    List<ActivityHistoryEntity> findAllByActivityTypeAndPrimaryUser(ActivityTypeEnum activityType, UserEntity user);

    List<ActivityHistoryEntity> findAllByPost(PostEntity post);

    List<ActivityHistoryEntity> findAllByPostAndPrimaryUser(PostEntity post, UserEntity user);

    List<ActivityHistoryEntity> findAllByComment(CommentEntity comment);

    List<ActivityHistoryEntity> findAllByCommentAndPrimaryUser(CommentEntity comment, UserEntity user);

    List<ActivityHistoryEntity> findAllByCommentAndPost(CommentEntity comment, PostEntity post);
}
