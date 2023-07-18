package fontys.sem3.its.meem.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "posts")
@SQLDelete(sql = "UPDATE posts SET deleted = true, post_url = null WHERE post_id=?")
//@Where(clause = "deleted=false")
//@FilterDef(name = "deletedUserFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
//@Filter(name = "deletedUserFilter", condition = "deleted = :isDeleted")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "post_id",
            updatable = false,
            nullable = false
    )
    private int postId;

    @NotNull
    @ManyToOne
    @JoinColumn(
            name = "post_author",
            updatable = false,
            nullable = false,
            referencedColumnName = "user_id"
    )
    private UserEntity postAuthor;

    @NotNull
    @ManyToOne
    @JoinColumn(
            name = "category_id",
            updatable = false,
            nullable = false
    )
    private CategoryEntity category;

    @NotNull
    @ManyToOne
    @JoinColumn(
            name = "group_id",
            nullable = false
    )
    private SortingGroupEntity group;

    @NotNull
    @Column(
            name = "date_posted",
            updatable = false,
            nullable = false
    )
    private Timestamp datePosted;

    @NotBlank
    @Length(min = 2, max = 140)
    @Column(
            name = "post_title",
            nullable = false
    )
    private String postTitle;

    @OneToOne
    @JoinColumn(
            name = "post_media",
            referencedColumnName = "media_id"
    )
    private MediaEntity postMedia;

    @NotNull
    @Column(name = "post_edited")
    private Boolean postEdited;

    @NotBlank
    @Length(max = 40)
    @Column(name = "post_url")
    private String postUrl;

    @Column(name = "explicit")
    private Boolean explicit;
    @Column(name = "deleted")
    private Boolean deleted;
}
