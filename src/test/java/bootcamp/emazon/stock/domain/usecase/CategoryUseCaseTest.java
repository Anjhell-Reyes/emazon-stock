package bootcamp.emazon.stock.domain.usecase;

import bootcamp.emazon.stock.domain.exception.*;
import bootcamp.emazon.stock.domain.model.Category;
import bootcamp.emazon.stock.domain.model.CustomPage;
import bootcamp.emazon.stock.domain.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryUseCaseTest {

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category(1L, "Electronics", "Devices and gadgets");
    }

    @Test
    void saveCategory_Success() {
        when(categoryPersistencePort.saveCategory(any(Category.class))).thenReturn(category);

        Category savedCategory = categoryUseCase.saveCategory(category);

        assertNotNull(savedCategory);
        assertEquals(category.getName(), savedCategory.getName());
        verify(categoryPersistencePort, times(1)).saveCategory(any(Category.class));
    }

    @Test
    void saveCategory_ShouldThrowNamenotnullException_WhenNameIsNull() {
        category.setName(null);

        assertThrows(NamenotnullException.class, () -> categoryUseCase.saveCategory(category));
    }

    @Test
    void saveCategory_ShouldThrowNameMax50CharactersException_WhenNameExceedsMaxLength() {
        category.setName("A".repeat(51)); // nombre de 51 caracteres

        assertThrows(NameMax50CharactersException.class, () -> categoryUseCase.saveCategory(category));
    }

    @Test
    void saveCategory_ShouldThrowDescriptionNotnullException_WhenDescriptionIsNull() {
        category.setDescription(null);

        assertThrows(DescriptionNotnullException.class, () -> categoryUseCase.saveCategory(category));
    }

    @Test
    void saveCategory_ShouldThrowDescriptionEmptyException_WhenDescriptionIsEmpty() {
        category.setDescription("");

        assertThrows(DescriptionEmptyException.class, () -> categoryUseCase.saveCategory(category));
    }

    @Test
    void saveCategory_ShouldThrowDescriptionMax90CharactersException_WhenDescriptionExceedsMaxLength() {
        category.setDescription("A".repeat(91)); // descripción de 91 caracteres

        assertThrows(DescriptionMax90CharactersException.class, () -> categoryUseCase.saveCategory(category));
    }

    @Test
    void getCategory_Success() {
        when(categoryPersistencePort.getCategory(anyString())).thenReturn(category);

        Category foundCategory = categoryUseCase.getCategory("Electronics");

        assertNotNull(foundCategory);
        assertEquals("Electronics", foundCategory.getName());
        verify(categoryPersistencePort, times(1)).getCategory(anyString());
    }

    @Test
    void testGetAllCategoriesSuccessfully() {
        // Arrange
        int page = 1;
        int size = 10;
        String sortBy = "name";
        boolean asc = true;
        int offset = (page - 1) * size;

        List<Category> categories = Collections.singletonList(new Category(1L, "Electronics", "All electronic items"));
        long totalElements = 1L;

        // Mocking
        when(categoryPersistencePort.getAllCategories(offset, size, sortBy, asc)).thenReturn(categories);
        when(categoryPersistencePort.countcategory()).thenReturn(totalElements);

        // Act
        CustomPage<Category> result = categoryUseCase.getAllCategories(page, size, sortBy, asc);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(page, result.getPageNumber());
        assertEquals(size, result.getPageSize());
        assertEquals(totalElements, result.getTotalElements());

        verify(categoryPersistencePort, times(1)).getAllCategories(offset, size, sortBy, asc);
        verify(categoryPersistencePort, times(1)).countcategory();
    }

    @Test
    void testGetAllCategoriesThrowsInvalidPageIndexException() {
        // Arrange
        int page = -1; // Valor inválido
        int size = 10;
        String sortBy = "name";
        boolean asc = true;

        // Act & Assert
        assertThrows(InvalidPageIndexException.class, () -> categoryUseCase.getAllCategories(page, size, sortBy, asc));

        // No se debe llamar a estos métodos
        verify(categoryPersistencePort, never()).getAllCategories(anyInt(), anyInt(), anyString(), anyBoolean());
        verify(categoryPersistencePort, never()).countcategory();
    }

    @Test
    void updateCategory_Success() {
        doNothing().when(categoryPersistencePort).updateCategory(any(Category.class));

        categoryUseCase.updateCategory(category);

        verify(categoryPersistencePort, times(1)).updateCategory(any(Category.class));
    }

    @Test
    void updateCategory_ShouldThrowNamenotnullException_WhenNameIsNull() {
        category.setName(null);

        assertThrows(NamenotnullException.class, () -> categoryUseCase.updateCategory(category));
    }

    @Test
    void updateCategory_ShouldThrowDescriptionNotnullException_WhenDescriptionIsNull() {
        category.setDescription(null);

        assertThrows(DescriptionNotnullException.class, () -> categoryUseCase.updateCategory(category));
    }

    @Test
    void deleteCategory_Success() {
        doNothing().when(categoryPersistencePort).deleteCategory(anyString());

        categoryUseCase.deleteCategory("Electronics");

        verify(categoryPersistencePort, times(1)).deleteCategory(anyString());
    }
}
