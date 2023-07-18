package fontys.sem3.its.meem.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "comments")
@SQLDelete(sql = "UPDATE comments SET deleted = true, comment_body = null WHERE comment_id=?")
//@Where(clause = "deleted=false")
//@FilterDef(name = "deletedCommentFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
//@Filter(name = "deletedCommentFilter", condition = "deleted = :isDeleted")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentEntity {
    /* note to self:
        The way it currently it is, you could stack an infinite amount of comment reply threads
        Could limit that by implementing a comment 'depth' level and not allow any more reply nestings
        at this level
        (thinking emoji)
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "comment_id",
            updatable = false,
            nullable = false
    )
    private int commentId;
    @NotNull
    @ManyToOne
    @JoinColumn(
            name = "comment_author",
            updatable = false,
            nullable = false,
            referencedColumnName = "user_id"
    )
    private UserEntity commentAuthor;
    @NotNull
    @ManyToOne
    @JoinColumn(
            name = "post_id",
            updatable = false,
            nullable = false
    )
    private PostEntity post;
    @NotNull
    @Column(
            name = "date_commented",
            updatable = false,
            nullable = false
    )
    private Timestamp dateCommented;
    @NotBlank
    @Column(
            name = "comment_body"
    )
    private String commentBody;
    @Column(
            name = "comment_edited",
            nullable = false
    )
    private Boolean commentEdited;
    @Column(name = "parent_comment_id")
    private int parentCommentId;
    @NotNull
    @Column(name = "depth")
    private int depth;
    @Column(name = "explicit")
    private Boolean explicit;
    @NotNull
    @Column(name = "deleted")
    private Boolean deleted;

}
