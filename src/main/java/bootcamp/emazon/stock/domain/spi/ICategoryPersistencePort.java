package bootcamp.emazon.stock.domain.spi;

import bootcamp.emazon.stock.domain.model.Category;
import bootcamp.emazon.stock.domain.model.CustomPage;

public interface ICategoryPersistencePort {

    Category saveCategory(Category category);

    Category getCategory(String categoryName);

    CustomPage<Category> getAllCategories(int offset, int limit, String sortBy, boolean asc);

    void updateCategory(Category category);
    void deleteCategory(String categoryName);

}
