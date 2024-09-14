package bootcamp.emazon.stock.domain.spi;

import bootcamp.emazon.stock.domain.model.Category;

import java.util.List;

public interface ICategoryPersistencePort {

    Category saveCategory(Category category);

    Category getCategory(String categoryName);

    List<Category> getAllCategories(int offset, int limit, String sortBy, boolean asc);

    void updateCategory(Category category);
    void deleteCategory(String categoryName);

    long countcategory();
}
