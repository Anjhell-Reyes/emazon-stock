package bootcamp.emazon.stock.application.dto.categoryDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategorySummaryResponse {
    private Long id;
    private String name;
}
