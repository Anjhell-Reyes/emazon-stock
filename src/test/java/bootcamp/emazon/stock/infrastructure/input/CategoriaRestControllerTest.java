package bootcamp.emazon.stock.infrastructure.input;

import bootcamp.emazon.stock.application.dto.categoriaDto.CategoriaRequest;
import bootcamp.emazon.stock.application.dto.categoriaDto.CategoriaResponse;
import bootcamp.emazon.stock.application.handler.categoriaHandler.ICategoriaHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoriaRestController.class)
public class CategoriaRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICategoriaHandler categoriaHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveCategoriaInStock() throws Exception {
        CategoriaRequest categoriaRequest = new CategoriaRequest();
        // Configura tu objeto categoriaRequest con los datos necesarios

        mockMvc.perform(MockMvcRequestBuilders.post("/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoriaRequest)))
                .andExpect(status().isCreated());
    }


    @Test
     void testGetCategoriaFromStock() throws Exception {
        CategoriaResponse categoriaResponse = new CategoriaResponse();
        // Configura tu objeto categoriaResponse con los datos necesarios
        when(categoriaHandler.getCategoriaFromStock("exampleName")).thenReturn(categoriaResponse);

        mockMvc.perform(get("/categorias/exampleName"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists()); // Verifica que el contenido existe
    }

    @Test
    void testGetCategoriasFromStock() throws Exception {
        // Preparar datos de prueba
        CategoriaResponse categoriaResponse = new CategoriaResponse();
        categoriaResponse.setId(1L);
        categoriaResponse.setNombre("Categoria1");
        categoriaResponse.setDescripcion("Descripcion1");
        Page<CategoriaResponse> categoriaPage = new PageImpl<>(Collections.singletonList(categoriaResponse));

        // Configurar Mockito para que devuelva los datos de prueba
        when(categoriaHandler.getAllCategoriasFromStock(any(Integer.class), any(Integer.class), any(String.class), any(String.class)))
                .thenReturn(categoriaPage);

        // Ejecutar la solicitud GET y verificar la respuesta
        mockMvc.perform(get("/categorias")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "nombre")
                        .param("direction", "asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(categoriaPage)));
    }


    @Test
     void testUpdateCategoriaInStock() throws Exception {
        CategoriaRequest categoriaRequest = new CategoriaRequest();
        // Configura tu objeto categoriaRequest con los datos necesarios

        mockMvc.perform(MockMvcRequestBuilders.put("/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoriaRequest)))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteCategoriaFromStock() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/categorias/exampleName"))
                .andExpect(status().isNoContent());
    }
}
