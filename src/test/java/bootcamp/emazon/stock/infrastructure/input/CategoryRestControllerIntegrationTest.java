package bootcamp.emazon.stock.infrastructure.input;

import bootcamp.emazon.stock.application.dto.categoryDto.CategoryRequest;
import bootcamp.emazon.stock.application.dto.categoryDto.CategoryResponse;
import bootcamp.emazon.stock.application.handler.categoryHandler.ICategoryHandler;
import bootcamp.emazon.stock.domain.pagination.CategoryPaginated;
import bootcamp.emazon.stock.domain.exception.CategoryAlreadyExistsException;
import bootcamp.emazon.stock.domain.exception.CategoryNotFoundException;
import bootcamp.emazon.stock.domain.exception.NoDataFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@WebMvcTest(CategoryRestController.class)
public class CategoryRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICategoryHandler categoryHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Puedes inicializar datos comunes aquí si es necesario
    }

    @Test
    void testSaveCategoryInStock() throws Exception {
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName("Test Category");
        categoryRequest.setDescription("Test Description");

        mockMvc.perform(MockMvcRequestBuilders.post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void testSaveCategoryInStock_Conflict() throws Exception {
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName("Test Category");
        categoryRequest.setDescription("Test Description");
        doThrow(new CategoryAlreadyExistsException()).when(categoryHandler).saveCategoryInStock(any(CategoryRequest.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    void testGetCategoryFromStock() throws Exception {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(1L);
        categoryResponse.setName("Test Category");
        categoryResponse.setDescription("Test Description");
        when(categoryHandler.getCategoryFromStock(anyString())).thenReturn(categoryResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/categories/{categoryName}", "Test Category")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Category"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Test Description"));
    }

    @Test
    void testGetCategoryFromStock_NotFound() throws Exception {
        when(categoryHandler.getCategoryFromStock(anyString())).thenThrow(new CategoryNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("/categories/{categoryName}", "Test Category")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testGetCategoriesFromStock() throws Exception {
        List<CategoryPaginated> categories = Collections.singletonList(new CategoryPaginated(1L, "Test Category", "Test Description"));
        when(categoryHandler.getAllCategoriesFromStock(anyInt(), anyInt(), anyString(), anyBoolean())).thenReturn(categories);

        mockMvc.perform(MockMvcRequestBuilders.get("/categories")
                        .param("page", "1")
                        .param("size", "10")
                        .param("sortBy", "name")
                        .param("asc", "true")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Test Category"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Test Description"));
    }

    @Test
    void testGetCategoriesFromStock_NotFound() throws Exception {
        when(categoryHandler.getAllCategoriesFromStock(anyInt(), anyInt(), anyString(), anyBoolean())).thenThrow(new NoDataFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("/categories")
                        .param("page", "1")
                        .param("size", "10")
                        .param("sortBy", "name")
                        .param("asc", "true")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testUpdateCategoryInStock() throws Exception {
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName("Updated Category");
        categoryRequest.setDescription("Updated Description");


        mockMvc.perform(MockMvcRequestBuilders.put("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testUpdateCategoryInStock_NotFound() throws Exception {
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName("Updated Category");
        categoryRequest.setDescription("Updated Description");
        doThrow(new CategoryNotFoundException()).when(categoryHandler).updateCategoryInStock(any(CategoryRequest.class));

        mockMvc.perform(MockMvcRequestBuilders.put("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testDeleteCategoryFromStock() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/categories/{categoryName}", "Test Category"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testDeleteCategoryFromStock_NotFound() throws Exception {
        doThrow(new CategoryNotFoundException()).when(categoryHandler).deleteCategoryInStock(anyString());

        mockMvc.perform(MockMvcRequestBuilders.delete("/categories/{categoryName}", "Test Category"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
