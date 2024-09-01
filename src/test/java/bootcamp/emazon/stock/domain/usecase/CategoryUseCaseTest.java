package bootcamp.emazon.stock.domain.usecase;

import bootcamp.emazon.stock.domain.exception.*;
import bootcamp.emazon.stock.domain.model.Category;
import bootcamp.emazon.stock.domain.pagination.CategoryPaginated;
import bootcamp.emazon.stock.domain.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

    @Test
    void testSaveCategory_Success() {
        // Arrange
        Category category = new Category(1L, "ValidName", "ValidDescription");
        when(categoryPersistencePort.saveCategory(any(Category.class))).thenReturn(category);

        // Act
        Category result = categoryUseCase.saveCategory(category);

        // Assert
        assertEquals(category, result);
        verify(categoryPersistencePort, times(1)).saveCategory(category);
    }

    @Test
    void testSaveCategory_InvalidName() {
        // Arrange
        Category category = new Category(1L, "", "ValidDescription");

        // Act & Assert
        assertThrows(NamenotnullException.class, () -> categoryUseCase.saveCategory(category));
    }

    @Test
    void testSaveCategory_InvalidDescription() {
        // Arrange
        Category category = new Category(1L, "ValidName", "");

        // Act & Assert
        assertThrows(DescriptionNotnullException.class, () -> categoryUseCase.saveCategory(category));
    }

    @Test
    void testGetCategory_Success() {
        // Arrange
        Category category = new Category(1L, "ValidName", "ValidDescription");
        when(categoryPersistencePort.getCategory("ValidName")).thenReturn(category);

        // Act
        Category result = categoryUseCase.getCategory("ValidName");

        // Assert
        assertEquals(category, result);
        verify(categoryPersistencePort, times(1)).getCategory("ValidName");
    }

    @Test
    void testGetAllCategories_Success() {
        // Arrange
        CategoryPaginated paginatedCategory = new CategoryPaginated(1L, "ValidName", "ValidDescription");
        List<CategoryPaginated> categories = Collections.singletonList(paginatedCategory);
        when(categoryPersistencePort.getAllCategories(anyInt(), anyInt(), anyString(), anyBoolean())).thenReturn(categories);

        // Act
        List<CategoryPaginated> result = categoryUseCase.getAllCategories(1, 10, "name", true);

        // Assert
        assertEquals(categories, result);
        verify(categoryPersistencePort, times(1)).getAllCategories(0, 10, "name", true);
    }

    @Test
    void testGetAllCategories_InvalidPage() {
        // Act & Assert
        assertThrows(InvalidPageIndexException.class, () -> categoryUseCase.getAllCategories(-1, 10, "name", true));
    }

    @Test
    void testUpdateCategory_Success() {
        // Arrange
        Category category = new Category(1L, "ValidName", "ValidDescription");
        doNothing().when(categoryPersistencePort).updateCategory(any(Category.class));

        // Act
        assertDoesNotThrow(() -> categoryUseCase.updateCategory(category));

        // Assert
        verify(categoryPersistencePort, times(1)).updateCategory(category);
    }

    @Test
    void testUpdateCategory_InvalidName() {
        // Arrange
        Category category = new Category(1L, "", "ValidDescription");

        // Act & Assert
        assertThrows(NamenotnullException.class, () -> categoryUseCase.updateCategory(category));
    }

    @Test
    void testDeleteCategory_Success() {
        // Arrange
        doNothing().when(categoryPersistencePort).deleteCategory("ValidName");

        // Act
        assertDoesNotThrow(() -> categoryUseCase.deleteCategory("ValidName"));

        // Assert
        verify(categoryPersistencePort, times(1)).deleteCategory("ValidName");
    }
}
