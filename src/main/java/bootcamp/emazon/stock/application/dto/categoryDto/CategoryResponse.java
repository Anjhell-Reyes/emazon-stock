package bootcamp.emazon.stock.application.dto.categoryDto;

import lombok.Data;

@Data
public class CategoryResponse {
    private Long id;
    private String name;
    private String description;

}
