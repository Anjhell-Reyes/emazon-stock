package bootcamp.emazon.stock.domain.usecase;
import bootcamp.emazon.stock.domain.api.ICategoriaServicePort;
import bootcamp.emazon.stock.domain.model.Categoria;
import bootcamp.emazon.stock.domain.spi.ICategoriaPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoriaUseCaseTest {

        @Mock
        private ICategoriaServicePort categoriaServicePort;

        @Mock
        private ICategoriaPersistencePort categoriaPersistencePort;

        @InjectMocks
        private CategoriaUseCase categoriaUseCase;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        public void testSaveCategoria() {
            Categoria categoria = new Categoria(1L, "Electrónica", "Productos electrónicos");
            when(categoriaPersistencePort.saveCategoria(categoria)).thenReturn(categoria);

            Categoria result = categoriaUseCase.saveCategoria(categoria);

            assertNotNull(result);
            assertEquals(categoria.getNombre(), result.getNombre());
            verify(categoriaPersistencePort, times(1)).saveCategoria(categoria);
        }


        @Test
         void testGetCategoria() {
            Categoria categoria = new Categoria(1L, "Electrónica", "Productos electrónicos");
            when(categoriaPersistencePort.getCategoria("Electrónica")).thenReturn(categoria);

            Categoria result = categoriaUseCase.getCategoria("Electrónica");

            assertNotNull(result);
            assertEquals("Electrónica", result.getNombre());
            verify(categoriaPersistencePort, times(1)).getCategoria("Electrónica");
        }

    @Test
    void getAllCategorias_shouldReturnPaginatedCategorias() {
        // Arrange
        Categoria categoria1 = new Categoria();
        categoria1.setId(1L);
        categoria1.setNombre("Categoria1");
        categoria1.setDescripcion("Descripcion1");
        Categoria categoria2 = new Categoria();
        categoria2.setId(2L);
        categoria2.setNombre("Categoria2");
        categoria2.setDescripcion("Descripcion2");

        List<Categoria> categorias = Arrays.asList(categoria1, categoria2);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Categoria> page = new PageImpl<>(categorias, pageable, categorias.size());

        when(categoriaPersistencePort.getAllCategorias(pageable)).thenReturn(page);

        // Act
        Page<Categoria> result = categoriaUseCase.getAllCategorias(pageable);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent().get(0).getNombre()).isEqualTo("Categoria1");
        assertThat(result.getContent().get(1).getNombre()).isEqualTo("Categoria2");
    }

    @Test
         void testUpdateCategoria() {
            Categoria categoria = new Categoria(1L, "Electrónica", "Productos electrónicos");

            doNothing().when(categoriaPersistencePort).updateCategoria(categoria);

            categoriaUseCase.updateCategoria(categoria);

            verify(categoriaPersistencePort, times(1)).updateCategoria(categoria);
        }

        @Test
         void testDeleteCategoria() {
            doNothing().when(categoriaPersistencePort).deleteCategoria("Electrónica");

            categoriaUseCase.deleteCategoria("Electrónica");

            verify(categoriaPersistencePort, times(1)).deleteCategoria("Electrónica");
        }
    }
