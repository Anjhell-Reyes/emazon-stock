package bootcamp.emazon.stock.infrastructure.out.mapper;
import bootcamp.emazon.stock.domain.model.Categoria;
import bootcamp.emazon.stock.infrastructure.out.entity.CategoriaEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoriaEntityMapperTest {

    private final CategoriaEntityMapper categoriaEntityMapper = Mappers.getMapper(CategoriaEntityMapper.class);

    @Test
     void whenToEntity_thenCorrect() {
        // Arrange
        Categoria categoria = new Categoria(1L, "Electronics", "Description");

        // Act
        CategoriaEntity categoriaEntity = categoriaEntityMapper.toEntity(categoria);

        // Assert
        assertThat(categoriaEntity).isNotNull();
        assertThat(categoriaEntity.getId()).isEqualTo(1L);
        assertThat(categoriaEntity.getNombre()).isEqualTo("Electronics");
        assertThat(categoriaEntity.getDescripcion()).isEqualTo("Description");
    }

    @Test
    public void whenToCategoria_thenCorrect() {
        // Arrange
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setId(1L);
        categoriaEntity.setNombre("Electronics");
        categoriaEntity.setDescripcion("Description");

        // Act
        Categoria categoria = categoriaEntityMapper.toCategoria(categoriaEntity);

        // Assert
        assertThat(categoria).isNotNull();
        assertThat(categoria.getId()).isEqualTo(1L);
        assertThat(categoria.getNombre()).isEqualTo("Electronics");
        assertThat(categoria.getDescripcion()).isEqualTo("Description");
    }

    @Test
    public void whenToCategoriaList_thenCorrect() {
        // Arrange
        CategoriaEntity categoriaEntity1 = new CategoriaEntity();
        categoriaEntity1.setId(1L);
        categoriaEntity1.setNombre("Electronics");
        categoriaEntity1.setDescripcion("Description");

        CategoriaEntity categoriaEntity2 = new CategoriaEntity();
        categoriaEntity2.setId(2L);
        categoriaEntity2.setNombre("Books");
        categoriaEntity2.setDescripcion("Books Description");

        List<CategoriaEntity> categoriaEntityList = Arrays.asList(categoriaEntity1, categoriaEntity2);

        // Act
        List<Categoria> categoriaList = categoriaEntityMapper.toCategoriaList(categoriaEntityList);

        // Assert
        assertThat(categoriaList).hasSize(2);

        Categoria categoria1 = categoriaList.get(0);
        assertThat(categoria1.getId()).isEqualTo(1L);
        assertThat(categoria1.getNombre()).isEqualTo("Electronics");
        assertThat(categoria1.getDescripcion()).isEqualTo("Description");

        Categoria categoria2 = categoriaList.get(1);
        assertThat(categoria2.getId()).isEqualTo(2L);
        assertThat(categoria2.getNombre()).isEqualTo("Books");
        assertThat(categoria2.getDescripcion()).isEqualTo("Books Description");
    }
}

