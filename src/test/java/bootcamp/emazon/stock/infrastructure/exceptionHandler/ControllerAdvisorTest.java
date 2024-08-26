package bootcamp.emazon.stock.infrastructure.exceptionHandler;

import bootcamp.emazon.stock.domain.api.ICategoriaServicePort;
import bootcamp.emazon.stock.infrastructure.exception.NoDataFoundException;
import bootcamp.emazon.stock.infrastructure.input.CategoriaRestController;
import bootcamp.emazon.stock.application.handler.categoriaHandler.ICategoriaHandler;
import bootcamp.emazon.stock.infrastructure.exception.CategoriaAlreadyExistsExeption;
import bootcamp.emazon.stock.infrastructure.exception.CategoriaNotFoundException;
import bootcamp.emazon.stock.application.exception.NamenotnullOrMax50Characters;
import bootcamp.emazon.stock.application.exception.DescriptionNotnullOrMax90Characters;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@WebMvcTest(controllers = CategoriaRestController.class)
public class ControllerAdvisorTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICategoriaServicePort categoriaServicePort;

    @MockBean
    private ICategoriaHandler categoriaHandler;

    @Test
    public void testHandleCategoriaAlreadyExistsException() throws Exception {
        Mockito.doThrow(new CategoriaAlreadyExistsExeption()).when(categoriaHandler)
                .saveCategoriaInStock(Mockito.any());

        mockMvc.perform(post("/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"ExistingCategory\"}"))
                .andExpect(status().isConflict())
                .andExpect(content().json("{\"Message\": \"Category already exists\"}"));
    }


    @Test
    public void testHandleCategoriaNotFoundException() throws Exception {
        Mockito.doThrow(new CategoriaNotFoundException()).when(categoriaHandler)
                .getCategoriaFromStock(Mockito.anyString());

        mockMvc.perform(get("/categorias/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"Message\": \"Category not found\"}"));
    }

    @Test
    public void testHandleNameNotNullOrMax50Characters() throws Exception {
        Mockito.doThrow(new NamenotnullOrMax50Characters()).when(categoriaHandler)
                .saveCategoriaInStock(Mockito.any());

        mockMvc.perform(post("/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"ThisNameIsDefinitelyWayTooLongForTheAllowedLength\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"Message\": \"Name must not be null and must be 50 characters or less\"}"));
    }

    @Test
    public void testHandleDescriptionNotNullOrMax90Characters() throws Exception {
        Mockito.doThrow(new DescriptionNotnullOrMax90Characters()).when(categoriaHandler)
                .saveCategoriaInStock(Mockito.any());

        mockMvc.perform(post("/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\": \"ThisDescriptionIsDefinitelyWayTooLongForTheAllowedLength\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"Message\": \"Description must not be null and must be 90 characters or less\"}"));
    }
    void testHandleNoDataFoundException() throws Exception {
        // Simula que el método getAllCategorias() lanza NoDataFoundException
        Mockito.when(categoriaServicePort.getAllCategorias(Mockito.any(Pageable.class)))
                .thenThrow(new NoDataFoundException());

        // Realiza la solicitud GET al endpoint /categorias
        mockMvc.perform(get("/categorias?page=0&size=10"))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"Message\": \"No data found\"}"));
    }
}
