package bootcamp.emazon.stock.application.handler.categoryHandler;

import bootcamp.emazon.stock.application.dto.categoryDto.CategoryPaginated;
import bootcamp.emazon.stock.application.dto.categoryDto.CategoryRequest;
import bootcamp.emazon.stock.application.dto.categoryDto.CategoryResponse;
import org.springframework.data.domain.Page;

public interface ICategoryHandler {

    void saveCategoryInStock(CategoryRequest categoryRequest);

    CategoryResponse getCategoryFromStock(String categoryName);

    void updateCategoryInStock(CategoryRequest categoryRequest);
    void deleteCategoryInStock(String categoryName);

    Page<CategoryPaginated> getAllCategoriesFromStock(int page, int size, String sortBy, boolean asc);

}
