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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
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
