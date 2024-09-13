package bootcamp.emazon.stock.infrastructure.out.adapter;

import bootcamp.emazon.stock.domain.exception.CategoryAlreadyExistsException;
import bootcamp.emazon.stock.domain.exception.CategoryNotFoundException;
import bootcamp.emazon.stock.domain.exception.NoDataFoundException;
import bootcamp.emazon.stock.domain.model.Category;
import bootcamp.emazon.stock.application.dto.categoryDto.CategoryPaginated;
import bootcamp.emazon.stock.infrastructure.out.entity.CategoryEntity;
import bootcamp.emazon.stock.infrastructure.out.mapper.CategoryEntityMapper;
import bootcamp.emazon.stock.infrastructure.out.repository.ICategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryJpaAdapterTest {

    @Mock
    private ICategoryRepository categoryRepository;

    @Mock
    private CategoryEntityMapper categoryEntityMapper;

    @InjectMocks
    private CategoryJpaAdapter categoryJpaAdapter;

    @Test
    void testSaveCategory_CategoryAlreadyExists() {
        Category category = new Category();
        category.setName("Test Category");
        category.setDescription("Test Description");
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(new CategoryEntity()));

        assertThrows(CategoryAlreadyExistsException.class, () -> categoryJpaAdapter.saveCategory(category));
    }

    @Test
    void testSaveCategory_Success() {
        Category category = new Category();
        category.setName("Test Category");
        category.setDescription("Test Description");
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(categoryEntityMapper.toEntity(any(Category.class))).thenReturn(new CategoryEntity());

        Category result = categoryJpaAdapter.saveCategory(category);

        assertEquals(category, result);
        verify(categoryRepository, times(1)).save(any(CategoryEntity.class));
    }

    @Test
    void testGetCategory_CategoryNotFound() {
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> categoryJpaAdapter.getCategory("Nonexistent Category"));
    }

    @Test
    void testGetCategory_Success() {
        CategoryEntity categoryEntity = new CategoryEntity();
        Category category = new Category();
        category.setName("Test Category");
        category.setDescription("Test Description");
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(categoryEntity));
        when(categoryEntityMapper.toCategory(any(CategoryEntity.class))).thenReturn(category);

        Category result = categoryJpaAdapter.getCategory("Test Category");

        assertNotNull(result);
        assertEquals("Test Category", result.getName());
    }

    @Test
    void testGetAllCategoriesWithResults() {
        // Arrange
        int offset = 0;
        int limit = 10;
        String sortBy = "name";
        boolean asc = true;

        Sort sort = Sort.by(asc ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(offset / limit, limit, sort);

        CategoryEntity categoryEntity = new CategoryEntity(); // Sample category entity
        Category category = new Category(); // Sample category

        when(categoryRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(categoryEntity)));
        when(categoryEntityMapper.toCategory(categoryEntity)).thenReturn(category);

        // Act
        List<Category> result = categoryJpaAdapter.getAllCategories(offset, limit, sortBy, asc);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(category, result.get(0));
        verify(categoryRepository).findAll(pageable);
        verify(categoryEntityMapper).toCategory(categoryEntity);
    }

    @Test
    void testGetAllCategoriesNoResults() {
        // Arrange
        int offset = 0;
        int limit = 10;
        String sortBy = "name";
        boolean asc = true;

        Sort sort = Sort.by(asc ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(offset / limit, limit, sort);

        when(categoryRepository.findAll(pageable)).thenReturn(Page.empty());

        // Act & Assert
        assertThrows(NoDataFoundException.class, () -> categoryJpaAdapter.getAllCategories(offset, limit, sortBy, asc));
        verify(categoryRepository).findAll(pageable);
    }

    @Test
    void testUpdateCategory() {
        Category category = new Category();
        category.setName("Test Category");
        category.setDescription("Test Description");
        when(categoryEntityMapper.toEntity(any(Category.class))).thenReturn(new CategoryEntity());

        assertDoesNotThrow(() -> categoryJpaAdapter.updateCategory(category));
        verify(categoryRepository, times(1)).save(any(CategoryEntity.class));
    }

    @Test
    void testDeleteCategory() {
        doNothing().when(categoryRepository).deleteByName(anyString());

        assertDoesNotThrow(() -> categoryJpaAdapter.deleteCategory("Test Category"));
        verify(categoryRepository, times(1)).deleteByName(anyString());
    }
}
