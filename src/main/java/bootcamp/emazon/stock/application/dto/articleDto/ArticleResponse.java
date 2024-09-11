package bootcamp.emazon.stock.application.dto.articleDto;

import lombok.Data;

import java.util.Set;

@Data
public class ArticleResponse {

    private Long id;
    private String name;
    private String description;
    private Integer quantity;
    private double price;
    private String brandName;
    private Set<String> categories;
}
