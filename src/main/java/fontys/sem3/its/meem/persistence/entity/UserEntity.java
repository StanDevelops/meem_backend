package fontys.sem3.its.meem.persistence.entity;

import fontys.sem3.its.meem.domain.model.UserRoleEnum;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_username",
                        columnNames = {"username"}
                ),
                @UniqueConstraint(
                        name = "unique_user_email",
                        columnNames = {"email"}
                )
        }
)
@SQLDelete(sql = "UPDATE users SET " +
        "deleted = true, " +
        "username = null, " +
        "email = null, " +
        "password = null, " +
        "gender = null, " +
        "user_rating = 0, " +
        "profile_picture = null, " +
        "user_role = 'DELETED' " +
        "WHERE user_id=?")
//@Where(clause = "deleted=false")
//@FilterDef(name = "deletedUserFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
//@Filter(name = "deletedUserFilter", condition = "deleted = :isDeleted")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserEntity {
    @Id
//    @SequenceGenerator(
//            name = "user_sequence_generator",
//            sequenceName = "user_sequence",
//            initialValue = 1000,
//            allocationSize = 1
//    )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "user_id",
            updatable = false,
            nullable = false
    )
    private int userId;

    @NotBlank
    @Length(min = 3, max = 21)
    @Column(
            name = "username"
    )
    private String username;

    @NotBlank
    @Length(min = 5, max = 50)
    @Column(
            name = "email"
    )
    private String email;

    @NotBlank
    @Column(
            name = "password"
    )
    private String password;

    @Column(
            name = "date_registered",
            updatable = false
    )
    private Timestamp dateRegistered;

    @Column(
            name = "gender"
    )
    private Character gender;

    @Column(
            name = "user_rating"
    )
    private int userRating;

    @Column(
            name = "honourable_user"
    )
    private Boolean honourableUser;
    @Enumerated(EnumType.STRING)
    @Column(
            name = "user_role"
    )
    private UserRoleEnum userRole;

    @Column(
            name = "is_banned"
    )
    private Boolean isBanned;

    @Column(name = "deleted")
    private Boolean deleted;

    @ManyToOne
    @JoinColumn(
            name = "profile_picture",
            referencedColumnName = "media_id"
    )
    private MediaEntity profilePicture;
}

//done
