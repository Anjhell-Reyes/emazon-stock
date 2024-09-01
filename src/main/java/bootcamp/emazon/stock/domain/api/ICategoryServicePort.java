package bootcamp.emazon.stock.domain.api;

import bootcamp.emazon.stock.domain.model.Category;
import bootcamp.emazon.stock.domain.pagination.CategoryPaginated;

import java.util.List;

public interface
ICategoryServicePort {

    Category saveCategory(Category category);

    Category getCategory(String categoryName);

    List<CategoryPaginated> getAllCategories(int page, int size, String sortBy, boolean asc);

    void updateCategory(Category category);
    void deleteCategory(String categoryName);

}
