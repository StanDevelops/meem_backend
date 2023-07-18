package fontys.sem3.its.meem.persistence.entity.keys;

import fontys.sem3.its.meem.persistence.entity.CommentEntity;
import fontys.sem3.its.meem.persistence.entity.UserEntity;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
public class CommentRatingCompositePrimaryKey implements Serializable {
    @NotNull
    @ManyToOne
    @JoinColumn(
            name = "comment_id",
            updatable = false,
            nullable = false
    )
    private CommentEntity commentId;

    @NotNull
    @ManyToOne
    @JoinColumn(
            name = "user_id",
            updatable = false,
            nullable = false
    )
    private UserEntity userId;
}
