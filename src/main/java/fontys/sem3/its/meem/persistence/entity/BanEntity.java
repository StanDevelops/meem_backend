package fontys.sem3.its.meem.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "bans")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "ban_id",
            updatable = false,
            nullable = false
    )
    private int banId;

    @NotNull
    @ManyToOne
    @JoinColumn(
            name = "user_id",
            updatable = false,
            nullable = false
    )
    private UserEntity userId;

    @NotNull
    @ManyToOne
    @JoinColumn(
            name = "mod_id",
            updatable = false,
            nullable = false,
            referencedColumnName = "user_id"
    )
    private UserEntity modId;

    @NotNull
    @ManyToOne
    @JoinColumn(
            name = "commandment_nr",
            updatable = false,
            nullable = false
    )
    private CommandmentEntity commandmentNr;

    @Column(
            name = "date_banned",
            updatable = false,
            nullable = false
    )
    private Timestamp dateBanned;

    @Column(
            name = "expiration_date",
            nullable = false
    )
    private Date expirationDate;

    @Column(
            name = "expired"
    )
    private Boolean expired;
}
