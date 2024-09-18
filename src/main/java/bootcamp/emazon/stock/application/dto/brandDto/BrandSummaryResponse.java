package bootcamp.emazon.stock.application.dto.brandDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BrandSummaryResponse {
    private Long id;
    private String name;
}
