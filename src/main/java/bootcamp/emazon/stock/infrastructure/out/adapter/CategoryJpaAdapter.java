package bootcamp.emazon.stock.infrastructure.out.adapter;

import bootcamp.emazon.stock.domain.exception.CategoryAlreadyExistsException;
import bootcamp.emazon.stock.domain.exception.CategoryNotFoundException;
import bootcamp.emazon.stock.domain.exception.NoDataFoundException;
import bootcamp.emazon.stock.domain.model.Category;
import bootcamp.emazon.stock.domain.spi.ICategoryPersistencePort;
import bootcamp.emazon.stock.infrastructure.out.entity.CategoryEntity;
import bootcamp.emazon.stock.infrastructure.out.mapper.CategoryEntityMapper;
import bootcamp.emazon.stock.infrastructure.out.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CategoryJpaAdapter implements ICategoryPersistencePort {

    private final ICategoryRepository categoryRepository;

    private final CategoryEntityMapper categoryEntityMapper;

    public ICategoryRepository getCategoryRepository() {
        return categoryRepository;
    }

    public CategoryEntityMapper getCategoryEntityMapper() {
        return categoryEntityMapper;
    }

    @Override
    public Category saveCategory(Category category){
        if(categoryRepository.findByName(category.getName()).isPresent()){
            throw new CategoryAlreadyExistsException();
        }
        categoryRepository.save(categoryEntityMapper.toEntity(category));
        return category;
    }
    @Override
    public Category getCategory(String categoryName){
        return categoryEntityMapper.toCategory(categoryRepository.findByName(categoryName).orElseThrow(CategoryNotFoundException::new));
    }

    @Override
    public List<Category> getAllCategories(int offset, int limit, String sortBy, boolean asc) {
        Sort sort = Sort.by(asc ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(offset / limit, limit, sort);

        Page<CategoryEntity> categoryPage = categoryRepository.findAll(pageable);

        if(categoryPage.isEmpty()){
            throw new NoDataFoundException();
        }
        return categoryPage.getContent().stream()
                .map(categoryEntityMapper::toCategory)
                .collect(Collectors.toList());
    }

    @Override
    public void updateCategory(Category category) { categoryRepository.save(categoryEntityMapper.toEntity(category));}

    @Override
    public void deleteCategory(String categoryName){ categoryRepository.deleteByName(categoryName);}

    @Override
    public long countArticles() {
        return categoryRepository.count();
    }

}

