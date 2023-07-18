package fontys.sem3.its.meem.domain.response.Category;

import fontys.sem3.its.meem.domain.model.Category;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetCategoriesResponse {
    private List<Category> categories;
}
