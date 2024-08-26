package bootcamp.emazon.stock.application.mapper;

import bootcamp.emazon.stock.application.dto.categoriaDto.CategoriaResponse;
import bootcamp.emazon.stock.domain.model.Categoria;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class CategoriaMapperTest {

    private final CategoriaMapper categoriaMapper = Mappers.getMapper(CategoriaMapper.class);

    @Test
    void whenToResponseList_thenCorrect() {
        // Arrange
        Categoria categoria1 = new Categoria(1L,"Electronics","Description");

        Categoria categoria2 = new Categoria(2L,"Books","Books Description");

        List<Categoria> categorias = Arrays.asList(categoria1, categoria2);

        // Act
        List<CategoriaResponse> categoriaResponses = categoriaMapper.toResponseList(categorias);

        // Assert
        assertThat(categoriaResponses).hasSize(2);

        CategoriaResponse response1 = categoriaResponses.get(0);
        assertThat(response1.getId()).isEqualTo(1L);
        assertThat(response1.getNombre()).isEqualTo("Electronics");
        assertThat(response1.getDescripcion()).isEqualTo("Description");

        CategoriaResponse response2 = categoriaResponses.get(1);
        assertThat(response2.getId()).isEqualTo(2L);
        assertThat(response2.getNombre()).isEqualTo("Books");
        assertThat(response2.getDescripcion()).isEqualTo("Books Description");
    }
}
