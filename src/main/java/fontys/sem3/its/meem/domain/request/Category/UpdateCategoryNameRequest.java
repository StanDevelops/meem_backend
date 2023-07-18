package fontys.sem3.its.meem.domain.request.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

//done
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryNameRequest {
    @NotBlank
    private int categoryId;
    @NotBlank
    @Length(min = 2, max = 69)
    private String newCategoryName;
}
