package fontys.sem3.its.meem.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

//done
@Data
@Builder
@AllArgsConstructor
//@NoArgsConstructor
public class FrontEndProfilePictureHistory {
    private int editId;
    private int mediaId;
    private int userId;
    private String mediaAddress;

}
