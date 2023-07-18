package fontys.sem3.its.meem.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "post_ratings",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_user_post_combination",
                        columnNames = {"post_id", "user_id"}
                )
        })
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
//@IdClass(PostRatingCompositePrimaryKey.class)
public class PostRatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "rating_id",
            updatable = false,
            nullable = false
    )
    private int ratingId;

    @NotNull
    @ManyToOne(targetEntity = PostEntity.class)
    @JoinColumn(
            name = "post_id",
            referencedColumnName = "post_id",
            updatable = false,
            nullable = false
    )
    private PostEntity postId;

    @NotNull
    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "user_id",
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
