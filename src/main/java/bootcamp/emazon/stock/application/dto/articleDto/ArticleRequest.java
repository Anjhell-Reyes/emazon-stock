package bootcamp.emazon.stock.application.dto.articleDto;

import bootcamp.emazon.stock.application.dto.categoryDto.CategoryRequest;
import bootcamp.emazon.stock.domain.model.Brand;
import bootcamp.emazon.stock.domain.model.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArticleRequest {

    private String name;
    private String description;
    private Integer quantity;
    private double price;
    private Brand brand;
    private List<CategoryRequest> categories;
}
