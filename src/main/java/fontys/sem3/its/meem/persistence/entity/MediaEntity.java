package fontys.sem3.its.meem.persistence.entity;

import fontys.sem3.its.meem.domain.model.MediaTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Entity
@Table(name = "media",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_media_name",
                        columnNames = {"media_name"}
                )
        })
@SQLDelete(sql = "UPDATE media SET deleted = true, media_address = '' WHERE media_id=?")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MediaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "media_id",
            nullable = false
    )
    private int mediaId;

    @Column(
            name = "media_type"
    )
    @Enumerated(EnumType.STRING)
    private MediaTypeEnum mediaType;

    @Column(
            name = "media_name"
    )
    private String mediaName;

    @Column(
            name = "media_address"
    )
    private String mediaAddress;

    @Column(
            name = "media_size"
    )
    private int mediaSize;

    @Column(name = "external")
    private Boolean external;

    @Column(name = "deleted")
    private Boolean deleted;
}
