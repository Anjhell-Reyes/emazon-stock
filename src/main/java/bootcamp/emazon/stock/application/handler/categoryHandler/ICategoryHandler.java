package bootcamp.emazon.stock.application.handler.categoryHandler;

import bootcamp.emazon.stock.application.dto.categoryDto.CategoryRequest;
import bootcamp.emazon.stock.application.dto.categoryDto.CategoryResponse;
import bootcamp.emazon.stock.domain.pagination.CategoryPaginated;

import java.util.List;

public interface ICategoryHandler {

    void saveCategoryInStock(CategoryRequest categoryRequest);

    CategoryResponse getCategoryFromStock(String categoryName);

    void updateCategoryInStock(CategoryRequest categoryRequest);
    void deleteCategoryInStock(String categoryName);

    List<CategoryPaginated> getAllCategoriesFromStock(int page, int size, String sortBy, boolean asc);

}
