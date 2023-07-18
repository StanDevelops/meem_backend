package fontys.sem3.its.meem.domain.request.Commandment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommandmentRequest {
    @NotBlank
    private String commandmentRule;
    @NotNull
    private String punishmentDuration;
}
