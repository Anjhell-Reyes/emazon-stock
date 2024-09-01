package bootcamp.emazon.stock.domain.spi;

import bootcamp.emazon.stock.domain.model.Category;
import bootcamp.emazon.stock.domain.pagination.CategoryPaginated;

import java.util.List;

public interface ICategoryPersistencePort {

    Category saveCategory(Category category);

    Category getCategory(String categoryName);

    List<CategoryPaginated> getAllCategories(int offset, int limit, String sortBy, boolean asc);

    void updateCategory(Category category);
    void deleteCategory(String categoryName);
}
