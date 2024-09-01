package bootcamp.emazon.stock.application.handler.categoriaHandler;

import bootcamp.emazon.stock.application.dto.categoriaDto.CategoryRequest;
import bootcamp.emazon.stock.application.dto.categoriaDto.CategoryResponse;
import bootcamp.emazon.stock.domain.pagination.CategoryPaginated;

import java.util.List;

public interface ICategoryHandler {

    void saveCategoryInStock(CategoryRequest categoryRequest);

    CategoryResponse getCategoryFromStock(String categoryName);

    void updateCategoryInStock(CategoryRequest categoryRequest);
    void deleteCategoryInStock(String categoryName);

    List<CategoryPaginated> getAllCategoriesFromStock(int page, int size, String sortBy, boolean asc);

}
