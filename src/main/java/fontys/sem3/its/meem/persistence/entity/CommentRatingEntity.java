package fontys.sem3.its.meem.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "comment_ratings",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_user_comment_combination",
                        columnNames = {"comment_id", "user_id"}
                )
        })
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "rating_id",
            updatable = false,
            nullable = false
    )
    private int ratingId;
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

    @NotNull
    @Column(
            name = "weight",
            nullable = false
    )
    private int weight;
}
