package fontys.sem3.its.meem.domain.response.Category;

import fontys.sem3.its.meem.domain.model.Category;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetCategoryResponse {
    private Category category;
}
