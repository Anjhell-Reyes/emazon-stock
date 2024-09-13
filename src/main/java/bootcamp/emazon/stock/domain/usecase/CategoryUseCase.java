package bootcamp.emazon.stock.domain.usecase;



import bootcamp.emazon.stock.domain.api.ICategoryServicePort;
import bootcamp.emazon.stock.domain.exception.*;
import bootcamp.emazon.stock.domain.model.Category;
 import bootcamp.emazon.stock.domain.model.CustomPage;
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
            public CustomPage<Category> getAllCategories(int page, int size, String sortBy, boolean asc) {
                if (page < 0) {
                    throw new InvalidPageIndexException();
                }
                int offset = (page - 1) * size;

                List<Category> categories = categoryPersistencePort.getAllCategories(offset, size, sortBy, asc);

                long totalElements = categoryPersistencePort.countArticles();

                return new CustomPage<>(categories, page, size, totalElements);
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
