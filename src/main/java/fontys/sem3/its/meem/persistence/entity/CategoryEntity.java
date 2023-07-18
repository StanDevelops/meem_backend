package fontys.sem3.its.meem.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "categories",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_category_name",
                        columnNames = {"category_name"}
                )
        }
)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "category_id",
            updatable = false,
            nullable = false
    )
    private int categoryId;

    @NotBlank
    @Length(min = 2, max = 21)
    @Column(
            name = "category_name",
            nullable = false
    )
    private String categoryName;
}

//done