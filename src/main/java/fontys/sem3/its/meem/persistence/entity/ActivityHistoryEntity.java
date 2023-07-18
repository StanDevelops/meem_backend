package fontys.sem3.its.meem.persistence.entity;

import fontys.sem3.its.meem.domain.model.ActivityTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "activity_history")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
//@IdClass(ActivityHistoryCompositePrimaryKey.class)
public class ActivityHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "activity_id"
    )
    private int activityId;
    @NotNull
    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(
            name = "primary_user_id",
            updatable = false,
            nullable = false
    )
    private UserEntity primaryUser;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(
            name = "secondary_user_id",
            updatable = false
    )
    private UserEntity secondaryUser;

    @NotNull
    @Column(
            name = "activity_timestamp",
            updatable = false,
            nullable = false
    )
    private Timestamp activityTimestamp;
    @NotNull
    @Column(
            name = "activity_type",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private ActivityTypeEnum activityType;

    @ManyToOne
    @JoinColumn(
            name = "post_id",
            updatable = false
    )
    private PostEntity post;

    @ManyToOne
    @JoinColumn(
            name = "comment_id",
            updatable = false
    )
    private CommentEntity comment;
    @ManyToOne
    @JoinColumn(
            name = "parent_comment_id",
            updatable = false
    )
    private CommentEntity parentComment;
}
