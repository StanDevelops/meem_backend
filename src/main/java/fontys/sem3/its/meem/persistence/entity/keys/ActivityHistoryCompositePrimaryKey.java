package fontys.sem3.its.meem.persistence.entity.keys;

import fontys.sem3.its.meem.persistence.entity.UserEntity;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

@NoArgsConstructor
@EqualsAndHashCode
public class ActivityHistoryCompositePrimaryKey implements Serializable {
    @NotNull
    @ManyToOne
    @JoinColumn(
            name = "user_id",
            updatable = false,
            nullable = false
    )
    private UserEntity userId;

    @NotNull
    @Column(
            name = "activity_timestamp",
            updatable = false,
            nullable = false
    )
    private Timestamp activityTimestamp;
}
