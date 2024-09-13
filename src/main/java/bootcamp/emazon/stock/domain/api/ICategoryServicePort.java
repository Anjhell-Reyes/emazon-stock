package bootcamp.emazon.stock.domain.api;

import bootcamp.emazon.stock.domain.model.Category;
import bootcamp.emazon.stock.domain.model.CustomPage;

public interface
ICategoryServicePort {

    Category saveCategory(Category category);

    Category getCategory(String categoryName);

    CustomPage<Category> getAllCategories(int page, int size, String sortBy, boolean asc);

    void updateCategory(Category category);
    void deleteCategory(String categoryName);

}
