package bootcamp.emazon.stock.domain.usecase;



import bootcamp.emazon.stock.domain.api.ICategoryServicePort;
import bootcamp.emazon.stock.domain.exception.*;
import bootcamp.emazon.stock.domain.model.Category;
import bootcamp.emazon.stock.domain.pagination.CategoryPaginated;
import bootcamp.emazon.stock.domain.spi.ICategoryPersistencePort;

import java.util.List;

public class CategoryUseCase implements ICategoryServicePort {

    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort){
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public Category saveCategory(Category category){

        int maxLenghtName = 50;
        int maxLenghtDescription = 90;

        if (category.getName() == null) {
            throw new NamenotnullException();
        }
        if(category.getName().length() > maxLenghtName){
            throw new NameMax50CharactersException();
        }
        if (category.getDescription() == null) {
            throw new DescriptionNotnullException();
        }
        if(category.getDescription().isEmpty()){
            throw new DescriptionEmptyException();
        }
        if(category.getDescription().length() > maxLenghtDescription) {
            throw new DescriptionMax90CharactersException();
        }
        return categoryPersistencePort.saveCategory(category);
    }

    @Override
    public Category getCategory(String categoryName){
        return categoryPersistencePort.getCategory(categoryName);
    }

    @Override
    public List<CategoryPaginated> getAllCategories(int page, int size, String sortBy, boolean asc) {
        if (page < 0) {
            throw new InvalidPageIndexException();
        }
        int offset = (page - 1) * size;
        return categoryPersistencePort.getAllCategories(offset, size, sortBy, asc);
    }

    @Override
    public void updateCategory(Category category) {
        if (category.getName() == null) {
            throw new NamenotnullException();
        }
        if (category.getDescription() == null) {
            throw new DescriptionNotnullException();
        }
        categoryPersistencePort.updateCategory(category);
    }

    @Override
    public void deleteCategory(String categoryName) {
        categoryPersistencePort.deleteCategory(categoryName);
    }

}
