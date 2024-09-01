package bootcamp.emazon.stock.infrastructure.out.entity;

import bootcamp.emazon.stock.domain.model.Brand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class BrandEntityTest {
    @Test
    void testBrandCreation() {
        // Arrange
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Electronics");
        brand.setDescription("All kinds of electronic items");

        // Act & Assert
        assertThat(brand.getId()).isEqualTo(1L);
        assertThat(brand.getName()).isEqualTo("Electronics");
        assertThat(brand.getDescription()).isEqualTo("All kinds of electronic items");
    }

    @Test
    void testBrandEqualsAndHashCode() {
        // Arrange
        Brand brand1 = new Brand();
        brand1.setId(1L);
        brand1.setName("Electronics");
        brand1.setDescription("All kinds of electronic items");

        Brand brand2 = new Brand();
        brand2.setId(1L);
        brand2.setName("Electronics");
        brand2.setDescription("All kinds of electronic items");

        // Act & Assert
        assertThat(brand1).isEqualTo(brand2);
        assertThat(brand1.hashCode()).isEqualTo(brand2.hashCode());
    }

    @Test
    void testBrandNotEquals() {
        // Arrange
        Brand brand1 = new Brand();
        brand1.setId(1L);
        brand1.setName("Electronics");
        brand1.setDescription("All kinds of electronic items");

        Brand brand2 = new Brand();
        brand2.setId(2L);
        brand2.setName("Books");
        brand2.setDescription("Various books");

        // Act & Assert
        assertThat(brand1).isNotEqualTo(brand2);
    }

    @Test
    void testBrandSettersAndGetters() {
        // Arrange
        Brand brand = new Brand();

        // Act
        brand.setId(1L);
        brand.setName("Electronics");
        brand.setDescription("All kinds of electronic items");

        // Assert
        assertThat(brand.getId()).isEqualTo(1L);
        assertThat(brand.getName()).isEqualTo("Electronics");
        assertThat(brand.getDescription()).isEqualTo("All kinds of electronic items");
    }

}