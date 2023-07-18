package fontys.sem3.its.meem.persistence.entity.keys;

import fontys.sem3.its.meem.persistence.entity.CommentEntity;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@EqualsAndHashCode
public class CommentEditHistoryCompositePrimaryKey implements Serializable {
    @NotNull
    @ManyToOne
    @JoinColumn(
            name = "comment_id",
            updatable = false,
            nullable = false
    )
    private CommentEntity commentId;

    @NotNull
    @Column(
            name = "date_edited",
            updatable = false,
            nullable = false
    )
    private Date dateEdited;
}
