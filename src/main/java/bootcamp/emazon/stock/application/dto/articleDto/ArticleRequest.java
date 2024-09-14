package bootcamp.emazon.stock.application.dto.articleDto;

import bootcamp.emazon.stock.domain.utils.Constants;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class ArticleRequest {

    @NotBlank(message = Constants.NAME_NOT_BLANK_MESSAGE)
    private String name;

    @NotBlank(message = Constants.DESCRIPTION_NOT_BLANK_MESSAGE)
    private String description;

    @NotNull(message = Constants.QUANTITY_NOT_NULL_MESSAGE)
    @Min(value = 1, message = Constants.QUANTITY_MIN_MESSAGE)
    private Integer quantity;

    @NotNull(message = Constants.PRICE_NOT_NULL_MESSAGE)
    @Positive(message = Constants.PRICE_POSITIVE_MESSAGE)
    private double price;

    @NotBlank(message = Constants.BRAND_NOT_BLANK_MESSAGE)
    private String brandName;

    @NotNull(message = Constants.CATEGORIES_NOT_NULL_MESSAGE)
    @Size(min = 1, max = 3, message = Constants.CATEGORIES_SIZE_MESSAGE)
    private List<String> categories;
}
