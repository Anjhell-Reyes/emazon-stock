package bootcamp.emazon.stock.application.handler.categoriaHandler;

import bootcamp.emazon.stock.application.dto.categoriaDto.CategoriaRequest;
import bootcamp.emazon.stock.application.dto.categoriaDto.CategoriaResponse;
import bootcamp.emazon.stock.application.exception.DescriptionNotnullOrMax90Characters;
import bootcamp.emazon.stock.application.exception.NamenotnullOrMax50Characters;
import bootcamp.emazon.stock.application.mapper.CategoriaMapper;
import bootcamp.emazon.stock.domain.api.ICategoriaServicePort;
import bootcamp.emazon.stock.domain.model.Categoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import org.springframework.data.domain.*;
import java.util.Collections;

public class CategoriaHandlerTest {

    @Mock
    private ICategoriaServicePort categoriaServicePort;

    @Mock
    private CategoriaMapper categoriaMapper;

    @InjectMocks
    private CategoriaHandler categoriaHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveCategoriaInStock_ShouldCallMapperAndService() {
        CategoriaRequest request = new CategoriaRequest();
        request.setNombre("Electrónica");
        request.setDescripcion("Productos electrónicos");

        Categoria categoria = new Categoria(1L, "testName", "descriptionTest");
        when(categoriaMapper.toCategoria(any(CategoriaRequest.class))).thenReturn(categoria);

        categoriaHandler.saveCategoriaInStock(request);

        verify(categoriaMapper, times(1)).toCategoria(request);
        verify(categoriaServicePort, times(1)).saveCategoria(categoria);
    }

    @Test
    void saveCategoriaInStock_NullNombre_ShouldThrowException() {
        CategoriaRequest request = new CategoriaRequest();
        request.setDescripcion("Productos electrónicos");

        assertThrows(NamenotnullOrMax50Characters.class, () -> categoriaHandler.saveCategoriaInStock(request));
    }

    @Test
    void saveCategoriaInStock_NombreTooLong_ShouldThrowException() {
        CategoriaRequest request = new CategoriaRequest();
        request.setNombre("Nombre demasiado largo que supera los 50 caracteres");
        request.setDescripcion("Productos electrónicos");

        assertThrows(NamenotnullOrMax50Characters.class, () -> categoriaHandler.saveCategoriaInStock(request));
    }

    @Test
    void saveCategoriaInStock_NullDescripcion_ShouldThrowException() {
        CategoriaRequest request = new CategoriaRequest();
        request.setNombre("Electrónica");

        assertThrows(DescriptionNotnullOrMax90Characters.class, () -> categoriaHandler.saveCategoriaInStock(request));
    }

    @Test
    void saveCategoriaInStock_DescripcionTooLong_ShouldThrowException() {
        CategoriaRequest request = new CategoriaRequest();
        request.setNombre("Electrónica");
        request.setDescripcion("Descripción demasiado larga que supera los 90 caracteres. Aquí se \" +\n" +
                "        \"agrega texto adicional para asegurarse de que la longitud total de la descripción es mayor \" +\n" +
                "        \"a 90 caracteres.");

        when(categoriaMapper.toCategoria(any(CategoriaRequest.class))).thenReturn(new Categoria());

        assertThrows(DescriptionNotnullOrMax90Characters.class, () -> categoriaHandler.saveCategoriaInStock(request));

        verify(categoriaMapper, never()).toCategoria(request);
        verify(categoriaServicePort, never()).saveCategoria(any(Categoria.class));
    }

    @Test
    void updateCategoriaInStock_ShouldUpdateCategoria() {
        CategoriaRequest categoriaRequest = new CategoriaRequest();
        categoriaRequest.setNombre("Electrónica");
        categoriaRequest.setDescripcion("Productos electrónicos");

        Categoria oldCategoria = new Categoria(1L, "Electrónica", "Descripción antigua");
        Categoria newCategoria = new Categoria(1L, "Electrónica", "Productos electrónicos");

        when(categoriaServicePort.getCategoria(categoriaRequest.getNombre())).thenReturn(oldCategoria);
        when(categoriaMapper.toCategoria(categoriaRequest)).thenReturn(newCategoria);

        categoriaHandler.updateCategoriaInStock(categoriaRequest);

        verify(categoriaServicePort, times(1)).getCategoria(categoriaRequest.getNombre());
        verify(categoriaMapper, times(1)).toCategoria(categoriaRequest);
        verify(categoriaServicePort, times(1)).updateCategoria(newCategoria);
    }

    @Test
    void deleteCategoriaInStock_ShouldCallDelete() {
        String categoriaNombre = "Electrónica";

        categoriaHandler.deleteCategoriaInStock(categoriaNombre);

        verify(categoriaServicePort, times(1)).deleteCategoria(categoriaNombre);
    }
}
