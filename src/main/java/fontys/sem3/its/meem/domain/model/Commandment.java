package fontys.sem3.its.meem.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

//done
@Data
@Builder
@AllArgsConstructor
//@NoArgsConstructor
public class Commandment {
    private int commandmentNr;
    private String commandmentRule;
    private String punishmentDuration;
}
