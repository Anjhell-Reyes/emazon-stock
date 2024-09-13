package bootcamp.emazon.stock.application.handler.categoryHandler;

import bootcamp.emazon.stock.application.dto.categoryDto.CategoryPaginated;
import bootcamp.emazon.stock.application.dto.categoryDto.CategoryRequest;
import bootcamp.emazon.stock.application.dto.categoryDto.CategoryResponse;
import bootcamp.emazon.stock.application.mapper.CategoryMapper;
import bootcamp.emazon.stock.domain.api.ICategoryServicePort;
import bootcamp.emazon.stock.domain.model.Category;
import bootcamp.emazon.stock.domain.model.CustomPage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryHandler implements ICategoryHandler {

    private final ICategoryServicePort categoryServicePort;
    private final CategoryMapper categoryMapper;

    @Override
    public void saveCategoryInStock(CategoryRequest categoryRequest){
        Category category = categoryMapper.toCategory(categoryRequest);
        categoryServicePort.saveCategory(category);
    }

    @Override
    public CategoryResponse getCategoryFromStock(String categoryName){
        Category category = categoryServicePort.getCategory(categoryName);
        return categoryMapper.toResponse(category);
    }

@Override
public Page<CategoryPaginated> getAllCategoriesFromStock(int page, int size, String sortBy, boolean asc) {
    CustomPage<Category> customPage = categoryServicePort.getAllCategories(page, size, sortBy, asc);

    List<CategoryPaginated> paginatedCategories = customPage.getContent().stream()
            .map(categoryMapper::toCategoryPaginated)
            .collect(Collectors.toList());

    return new PageImpl<>(paginatedCategories, PageRequest.of(customPage.getPageNumber(), customPage.getPageSize()), customPage.getTotalElements());
    }

    @Override
    public void updateCategoryInStock(CategoryRequest categoryRequest){
        Category oldCategory = categoryServicePort.getCategory(categoryRequest.getName());
        Category newCategory = categoryMapper.toCategory(categoryRequest);
        newCategory.setId(oldCategory.getId());
        newCategory.setName(oldCategory.getName());
        newCategory.setDescription(oldCategory.getDescription());
        categoryServicePort.updateCategory(newCategory);
    }

    @Override
    public void deleteCategoryInStock(String categoryNombre){
        categoryServicePort.deleteCategory(categoryNombre);
    }
}
