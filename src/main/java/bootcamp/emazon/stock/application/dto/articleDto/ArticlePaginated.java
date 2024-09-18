package bootcamp.emazon.stock.application.dto.articleDto;

import bootcamp.emazon.stock.application.dto.brandDto.BrandSummaryResponse;
import bootcamp.emazon.stock.application.dto.categoryDto.CategorySummaryResponse;
import lombok.Data;

import java.util.List;

@Data
public class ArticlePaginated {
    private String name;
    private String description;
    private Integer quantity;
    private double price;
    private BrandSummaryResponse brand;
    private List<CategorySummaryResponse> categories;
}
