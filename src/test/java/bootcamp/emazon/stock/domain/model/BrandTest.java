package bootcamp.emazon.stock.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BrandTest {

    @Test
    void testDefaultConstructor() {
        // Arrange
        Brand brand = new Brand();

        // Act & Assert
        assertNull(brand.getId(), "ID should be null by default");
        assertNull(brand.getName(), "Name should be null by default");
        assertNull(brand.getDescription(), "Description should be null by default");
    }

    @Test
    void testParameterizedConstructor() {
        // Arrange
        Long id = 1L;
        String name = "TestBrandName";
        String description = "TestBrandDescription";

        // Act
        Brand brand = new Brand(id, name, description);

        // Assert
        assertEquals(id, brand.getId(), "ID should be set correctly");
        assertEquals(name, brand.getName(), "Name should be set correctly");
        assertEquals(description, brand.getDescription(), "Description should be set correctly");
    }

    @Test
    void testConstructorWithInvalidData() {
        // Arrange
        String invalidName = ""; // Empty name
        String validDescription = "ValidDescription";

        // Act
        Brand brand = new Brand(1L, invalidName, validDescription);

        // Assert
        assertNull(brand.getName(), "Name should be null when it's empty");
        assertEquals(validDescription, brand.getDescription(), "Description should be set correctly");
    }

    @Test
    void testSetters() {
        // Arrange
        Brand brand = new Brand();
        Long id = 2L;
        String name = "AnotherBrandName";
        String description = "AnotherBrandDescription";

        // Act
        brand.setId(id);
        brand.setName(name);
        brand.setDescription(description);

        // Assert
        assertEquals(id, brand.getId(), "ID should be set correctly");
        assertEquals(name, brand.getName(), "Name should be set correctly");
        assertEquals(description, brand.getDescription(), "Description should be set correctly");
    }

}