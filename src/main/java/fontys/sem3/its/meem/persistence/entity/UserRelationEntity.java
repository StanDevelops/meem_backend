package fontys.sem3.its.meem.persistence.entity;

import fontys.sem3.its.meem.domain.model.UserRelationTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user_relations",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_users_relation_combination",
                        columnNames = {"primary_user_id", "secondary_user_id"}
                )
        })
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRelationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "relation_id",
            updatable = false,
            nullable = false
    )
    private int relationId;
    @NotNull
    @ManyToOne
    @JoinColumn(
            name = "primary_user_id",
            updatable = false,
            nullable = false
    )
    private UserEntity primaryUser;

    @NotNull
    @ManyToOne
    @JoinColumn(
            name = "secondary_user_id",
            updatable = false,
            nullable = false
    )
    private UserEntity secondaryUser;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(
            name = "relation_type",
            nullable = false
    )
    private UserRelationTypeEnum relationType;
}
