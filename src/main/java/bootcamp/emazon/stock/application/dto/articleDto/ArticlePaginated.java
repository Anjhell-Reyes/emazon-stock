package bootcamp.emazon.stock.application.dto.articleDto;

import lombok.Data;

import java.util.List;

@Data
public class ArticlePaginated {
    private String name;
    private String description;
    private Integer quantity;
    private double price;
    private String brandName;
    private List<String> categoryNames;
}
