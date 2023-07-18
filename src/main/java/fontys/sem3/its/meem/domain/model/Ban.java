package fontys.sem3.its.meem.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

//done
@Data
@Builder
@AllArgsConstructor
//@NoArgsConstructor
public class Ban {
    private int banId;
    private int userId;
    private int modId;
    private int commandmentNr;
    private Timestamp dateBanned;
    private Date expirationDate;
    private Boolean expired;
}
