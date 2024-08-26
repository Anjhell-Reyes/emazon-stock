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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoriaUseCaseTest {

        @Autowired
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
