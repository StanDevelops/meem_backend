package fontys.sem3.its.meem.persistence.entity.keys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class PostRatingCompositePrimaryKey implements Serializable {

    private int postId;
    private int userId;
}
