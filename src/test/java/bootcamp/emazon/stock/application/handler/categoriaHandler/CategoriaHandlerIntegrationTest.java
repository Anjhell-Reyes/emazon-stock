package bootcamp.emazon.stock.application.handler.categoriaHandler;

import bootcamp.emazon.stock.application.dto.categoriaDto.CategoriaRequest;
import bootcamp.emazon.stock.application.dto.categoriaDto.CategoriaResponse;
import bootcamp.emazon.stock.application.mapper.CategoriaMapper;
import bootcamp.emazon.stock.domain.api.ICategoriaServicePort;
import bootcamp.emazon.stock.domain.model.Categoria;
import bootcamp.emazon.stock.infrastructure.out.repository.ICategoriaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CategoriaHandlerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ICategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaHandler categoriaHandler;

    @MockBean
    private ICategoriaServicePort categoriaServicePort;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @MockBean
    private CategoriaMapper categoriaMapper;

    @Test
    void testSaveCategoriaInStock() throws Exception {
        // Arrange
        CategoriaRequest request = new CategoriaRequest();
        request.setNombre("Categoria Test");
        request.setDescripcion("Descripción de prueba");

        // Act and Assert
        mockMvc.perform(post("/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());  // Cambia a isCreated() para esperar 201
    }


    @Test
    void testGetCategoriaFromStock() throws Exception {
        // Arrange
        String categoriaNombre = "Categoria Test";
        Categoria categoria = new Categoria();
        categoria.setNombre(categoriaNombre);
        categoria.setDescripcion("Una descripción válida");

        CategoriaResponse response = new CategoriaResponse();
        response.setNombre(categoriaNombre);
        response.setDescripcion("Una descripción válida");

        when(categoriaServicePort.getCategoria(categoriaNombre)).thenReturn(categoria);
        when(categoriaMapper.toResponse(categoria)).thenReturn(response);

        // Act and Assert
        mockMvc.perform(get("/categorias/{nombre}", categoriaNombre))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value(categoriaNombre));
    }
    @Test
    void getAllCategoriasFromStock_shouldReturnPaginatedCategoriaResponses() {
        // Arrange
        int page = 0;
        int size = 10;
        String sort = "nombre";
        String direction = "ASC";

        Categoria categoria1 = new Categoria();
        categoria1.setId(1L);
        categoria1.setNombre("Categoria1");
        categoria1.setDescripcion("Decripcion1");
        Categoria categoria2 = new Categoria();
        categoria2.setId(2L);
        categoria2.setNombre("Categoria2");
        categoria2.setDescripcion("Decripcion2");
        List<Categoria> categorias = List.of(categoria1, categoria2);

        CategoriaResponse response1 = new CategoriaResponse();
        response1.setId(1L);
        response1.setNombre("Categoria1");
        response1.setDescripcion("Descripcion1");
        CategoriaResponse response2 = new CategoriaResponse();
        response2.setId(2L);
        response2.setNombre("Categoria2");
        response2.setDescripcion("Descripcion2");
        List<CategoriaResponse> categoriaResponses = List.of(response1, response2);

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));
        Page<Categoria> categoriaPage = new PageImpl<>(categorias, pageable, categorias.size());

        when(categoriaServicePort.getAllCategorias(pageable)).thenReturn(categoriaPage);
        when(categoriaMapper.toResponse(categoria1)).thenReturn(response1);
        when(categoriaMapper.toResponse(categoria2)).thenReturn(response2);

        // Act
        Page<CategoriaResponse> result = categoriaHandler.getAllCategoriasFromStock(page, size, sort, direction);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(categorias.size());
        assertThat(result.getContent()).isEqualTo(categoriaResponses);
    }
    @Test
    void testUpdateCategoriaInStock() throws Exception {
        // Datos de prueba
        CategoriaRequest request = new CategoriaRequest();
        request.setNombre("Categoria Test");
        request.setDescripcion("Descripción actualizada");

        Categoria oldCategoria = new Categoria();
        oldCategoria.setId(1L);
        oldCategoria.setNombre("Categoria Test");
        oldCategoria.setDescripcion("Descripción anterior");

        Categoria newCategoria = new Categoria();
        newCategoria.setId(1L);
        newCategoria.setNombre("Categoria Test");
        newCategoria.setDescripcion("Descripción actualizada");

        // Mockear el comportamiento de los servicios y mappers
        when(categoriaServicePort.getCategoria(anyString())).thenReturn(oldCategoria);
        when(categoriaMapper.toCategoria(any(CategoriaRequest.class))).thenReturn(newCategoria);

        // Ejecutar la solicitud PUT y verificar el resultado
        mockMvc.perform(MockMvcRequestBuilders.put("/categorias", "Categoria Test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        // Verificar que el servicio de actualización fue llamado con el objeto correcto
        verify(categoriaServicePort).updateCategoria(newCategoria);
    }


    @Test
    void testDeleteCategoriaInStock() throws Exception {
        String categoriaNombre = "Categoria Test";

        mockMvc.perform(delete("/categorias/{nombre}", categoriaNombre))
                .andExpect(status().isNoContent());
    }
}
