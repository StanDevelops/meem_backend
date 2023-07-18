package fontys.sem3.its.meem.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

//done
@Data
@Builder
@AllArgsConstructor
//@NoArgsConstructor
public class Media {
    private int mediaId;
    private MediaTypeEnum mediaType;
    private String mediaName;
    private String mediaAddress;
    private int mediaSize;
    private Boolean external;
    private Boolean deleted;
}
