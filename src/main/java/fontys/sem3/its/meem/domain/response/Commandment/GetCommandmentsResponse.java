package fontys.sem3.its.meem.domain.response.Commandment;

import fontys.sem3.its.meem.domain.model.Commandment;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetCommandmentsResponse {
    List<Commandment> commandments;
}
