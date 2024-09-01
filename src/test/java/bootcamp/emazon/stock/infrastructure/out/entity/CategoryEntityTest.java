package bootcamp.emazon.stock.infrastructure.out.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class CategoryEntityTest {

    @Test
    void testCategoryEntityCreation() {
        // Arrange
        CategoryEntity category = new CategoryEntity();
        category.setId(1L);
        category.setName("Electronics");
        category.setDescription("All kinds of electronic items");

        // Act & Assert
        assertThat(category.getId()).isEqualTo(1L);
        assertThat(category.getName()).isEqualTo("Electronics");
        assertThat(category.getDescription()).isEqualTo("All kinds of electronic items");
    }

    @Test
    void testCategoryEntityEqualsAndHashCode() {
        // Arrange
        CategoryEntity category1 = new CategoryEntity();
        category1.setId(1L);
        category1.setName("Electronics");
        category1.setDescription("All kinds of electronic items");

        CategoryEntity category2 = new CategoryEntity();
        category2.setId(1L);
        category2.setName("Electronics");
        category2.setDescription("All kinds of electronic items");

        // Act & Assert
        assertThat(category1).isEqualTo(category2);
        assertThat(category1.hashCode()).isEqualTo(category2.hashCode());
    }

    @Test
    void testCategoryEntityNotEquals() {
        // Arrange
        CategoryEntity category1 = new CategoryEntity();
        category1.setId(1L);
        category1.setName("Electronics");
        category1.setDescription("All kinds of electronic items");

        CategoryEntity category2 = new CategoryEntity();
        category2.setId(2L);
        category2.setName("Books");
        category2.setDescription("Various books");

        // Act & Assert
        assertThat(category1).isNotEqualTo(category2);
    }

    @Test
    void testCategoryEntitySettersAndGetters() {
        // Arrange
        CategoryEntity category = new CategoryEntity();

        // Act
        category.setId(1L);
        category.setName("Electronics");
        category.setDescription("All kinds of electronic items");

        // Assert
        assertThat(category.getId()).isEqualTo(1L);
        assertThat(category.getName()).isEqualTo("Electronics");
        assertThat(category.getDescription()).isEqualTo("All kinds of electronic items");
    }
}