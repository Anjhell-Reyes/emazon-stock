package bootcamp.emazon.stock.infrastructure.out.adapter;

import bootcamp.emazon.stock.domain.exception.CategoryAlreadyExistsException;
import bootcamp.emazon.stock.domain.exception.CategoryNotFoundException;
import bootcamp.emazon.stock.domain.exception.NoDataFoundException;
import bootcamp.emazon.stock.domain.model.Category;
import bootcamp.emazon.stock.application.dto.categoryDto.CategoryPaginated;
import bootcamp.emazon.stock.domain.model.CustomPage;
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

import java.util.Arrays;
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
    public void testGetAllCategories_Success() {
        // Datos de prueba
        CategoryEntity categoryEntity1 = new CategoryEntity();
        categoryEntity1.setId(1L);
        categoryEntity1.setName("Category 1");
        categoryEntity1.setName("Description 1");
        CategoryEntity categoryEntity2 = new CategoryEntity();
        categoryEntity2.setId(2L);
        categoryEntity2.setName("Category 2");
        categoryEntity2.setName("Description 2");
        List<CategoryEntity> categoryEntities = Arrays.asList(categoryEntity1, categoryEntity2);

        Page<CategoryEntity> categoryPage = new PageImpl<>(categoryEntities, PageRequest.of(0, 10), 2);

        Category category1 = new Category(1L, "Category 1", "Description 1");
        Category category2 = new Category(2L, "Category 2", "Description 2");
        List<Category> categories = Arrays.asList(category1, category2);

        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(categoryPage);
        when(categoryEntityMapper.toCategory(categoryEntity1)).thenReturn(category1);
        when(categoryEntityMapper.toCategory(categoryEntity2)).thenReturn(category2);

        CustomPage<Category> result = categoryJpaAdapter.getAllCategories(0, 10, "name", true);

        assertNotNull(result);
        assertEquals(0, result.getPageNumber());
        assertEquals(10, result.getPageSize());
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(categories, result.getContent());
    }

    @Test
    public void testGetAllCategories_NoDataFound() {
        Page<CategoryEntity> categoryPage = new PageImpl<>(List.of(), PageRequest.of(0, 10), 0);

        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(categoryPage);

        assertThrows(NoDataFoundException.class, () -> categoryJpaAdapter.getAllCategories(0, 10, "name", true));
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
