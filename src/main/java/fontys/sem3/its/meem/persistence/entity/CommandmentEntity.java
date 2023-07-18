package fontys.sem3.its.meem.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "commandments", uniqueConstraints = {
        @UniqueConstraint(
                name = "unique_commandment_rule",
                columnNames = {"commandment_rule"}
        )
})
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommandmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "commandment_nr",
            updatable = false,
            nullable = false
    )
    private int commandmentNr;

    @NotBlank
    @Length(max = 100)
    @Column(
            name = "commandment_rule",
            nullable = false
    )
    private String commandmentRule;

    @NotBlank
    @Length(max = 20)
    @Column(
            name = "punishment_duration",
            nullable = false
    )
    private String punishmentDuration;
}
