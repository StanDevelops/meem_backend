package fontys.sem3.its.meem.persistence.entity.keys;

import fontys.sem3.its.meem.persistence.entity.PostEntity;
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
public class PostEditHistoryCompositePrimaryKey implements Serializable {
    @NotNull
    @ManyToOne
    @JoinColumn(
            name = "post_id",
            updatable = false,
            nullable = false
    )
    private PostEntity postId;

    @NotNull
    @Column(
            name = "date_edited",
            updatable = false,
            nullable = false
    )
    private Timestamp dateEdited;
}
