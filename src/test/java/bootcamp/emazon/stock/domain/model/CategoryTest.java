package bootcamp.emazon.stock.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {

    @Test
    void testDefaultConstructor() {
        // Arrange
        Category category = new Category();

        // Act & Assert
        assertNull(category.getId(), "ID should be null by default");
        assertNull(category.getName(), "Name should be null by default");
        assertNull(category.getDescription(), "Description should be null by default");
    }

    @Test
    void testParameterizedConstructor() {
        // Arrange
        Long id = 1L;
        String name = "TestCategoryName";
        String description = "TestCategoryDescription";

        // Act
        Category category = new Category(id, name, description);

        // Assert
        assertEquals(id, category.getId(), "ID should be set correctly");
        assertEquals(name, category.getName(), "Name should be set correctly");
        assertEquals(description, category.getDescription(), "Description should be set correctly");
    }

    @Test
    void testConstructorWithInvalidData() {
        // Arrange
        String invalidName = ""; // Empty name
        String validDescription = "ValidDescription";

        // Act
        Category category = new Category(1L, invalidName, validDescription);

        // Assert
        assertNull(category.getName(), "Name should be null when it's empty");
        assertEquals(validDescription, category.getDescription(), "Description should be set correctly");
    }

    @Test
    void testSetters() {
        // Arrange
        Category category = new Category();
        Long id = 2L;
        String name = "AnotherCategoryName";
        String description = "AnotherCategoryDescription";

        // Act
        category.setId(id);
        category.setName(name);
        category.setDescription(description);

        // Assert
        assertEquals(id, category.getId(), "ID should be set correctly");
        assertEquals(name, category.getName(), "Name should be set correctly");
        assertEquals(description, category.getDescription(), "Description should be set correctly");
    }
}
