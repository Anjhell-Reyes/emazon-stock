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