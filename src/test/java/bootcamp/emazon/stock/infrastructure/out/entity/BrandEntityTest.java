package bootcamp.emazon.stock.infrastructure.out.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class BrandEntityTest {

    @Test
    void testBrandEntityCreation() {
        // Arrange
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setId(1L);
        brandEntity.setName("Electronics");
        brandEntity.setDescription("All kinds of electronic items");

        // Act & Assert
        assertThat(brandEntity.getId()).isEqualTo(1L);
        assertThat(brandEntity.getName()).isEqualTo("Electronics");
        assertThat(brandEntity.getDescription()).isEqualTo("All kinds of electronic items");
    }

    @Test
    void testBrandEntityEqualsAndHashCode() {
        // Arrange
        BrandEntity brandEntity1 = new BrandEntity();
        brandEntity1.setId(1L);
        brandEntity1.setName("Electronics");
        brandEntity1.setDescription("All kinds of electronic items");

        BrandEntity brandEntity2 = new BrandEntity();
        brandEntity2.setId(1L);
        brandEntity2.setName("Electronics");
        brandEntity2.setDescription("All kinds of electronic items");

        // Act & Assert
        assertThat(brandEntity1).isEqualTo(brandEntity2);
        assertThat(brandEntity1.hashCode()).isEqualTo(brandEntity2.hashCode());
    }

    @Test
    void testBrandEntityNotEquals() {
        // Arrange
        BrandEntity brandEntity1 = new BrandEntity();
        brandEntity1.setId(1L);
        brandEntity1.setName("Electronics");
        brandEntity1.setDescription("All kinds of electronic items");

        BrandEntity brandEntity2 = new BrandEntity();
        brandEntity2.setId(2L);
        brandEntity2.setName("Books");
        brandEntity2.setDescription("Various books");

        // Act & Assert
        assertThat(brandEntity1).isNotEqualTo(brandEntity2);
    }

    @Test
    void testBrandEntitySettersAndGetters() {
        // Arrange
        BrandEntity brandEntity = new BrandEntity();

        // Act
        brandEntity.setId(1L);
        brandEntity.setName("Electronics");
        brandEntity.setDescription("All kinds of electronic items");

        // Assert
        assertThat(brandEntity.getId()).isEqualTo(1L);
        assertThat(brandEntity.getName()).isEqualTo("Electronics");
        assertThat(brandEntity.getDescription()).isEqualTo("All kinds of electronic items");
    }
}