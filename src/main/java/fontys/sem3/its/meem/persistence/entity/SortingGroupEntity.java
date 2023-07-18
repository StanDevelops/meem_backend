package fontys.sem3.its.meem.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "sorting_groups",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_sorting_group_name",
                        columnNames = {"group_name"}
                )
        }
)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SortingGroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "group_id",
            updatable = false,
            nullable = false
    )
    private int groupId;

    @NotBlank
    @Length(min = 3, max = 15)
    @Column(
            name = "group_name",
            nullable = false
    )
    private String groupName;

    @Column(
            name = "group_rank",
            nullable = false
    )
    private int groupRank;
}

//done
