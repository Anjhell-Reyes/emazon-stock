package bootcamp.emazon.stock.infrastructure.input;

import bootcamp.emazon.stock.application.dto.categoryDto.CategoryRequest;
import bootcamp.emazon.stock.application.dto.categoryDto.CategoryResponse;
import bootcamp.emazon.stock.application.handler.categoryHandler.ICategoryHandler;
import bootcamp.emazon.stock.application.dto.categoryDto.CategoryPaginated;
import bootcamp.emazon.stock.domain.exception.CategoryAlreadyExistsException;
import bootcamp.emazon.stock.domain.exception.CategoryNotFoundException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryRestController.class)
public class CategoryRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICategoryHandler categoryHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSaveCategoryInStock() throws Exception {
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName("Test Category");
        categoryRequest.setDescription("Test Description");

        mockMvc.perform(MockMvcRequestBuilders.post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isCreated());
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
                .andExpect(status().isConflict());
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
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Category"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Test Description"));
    }

    @Test
    void testGetCategoryFromStock_NotFound() throws Exception {
        when(categoryHandler.getCategoryFromStock(anyString())).thenThrow(new CategoryNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("/categories/{categoryName}", "Test Category")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetCategoriesFromStockWithResults() throws Exception {
        // Arrange
        int page = 1;
        int size = 10;
        String sortBy = "name";
        boolean asc = true;

        CategoryPaginated categoryPaginated = new CategoryPaginated(); // Create a sample CategoryPaginated
        Page<CategoryPaginated> paginatedCategories = new PageImpl<>(List.of(categoryPaginated), PageRequest.of(page - 1, size), 1);

        when(categoryHandler.getAllCategoriesFromStock(page, size, sortBy, asc))
                .thenReturn(paginatedCategories);

        // Act & Assert
        mockMvc.perform(get("/categories")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("sortBy", sortBy)
                        .param("asc", String.valueOf(asc))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetCategoriesFromStockNoResults() throws Exception {
        // Arrange
        int page = 1;
        int size = 10;
        String sortBy = "name";
        boolean asc = true;

        Page<CategoryPaginated> paginatedCategories = new PageImpl<>(Collections.emptyList(), PageRequest.of(page - 1, size), 0);

        when(categoryHandler.getAllCategoriesFromStock(page, size, sortBy, asc))
                .thenReturn(paginatedCategories);

        // Act & Assert
        mockMvc.perform(get("/categories")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("sortBy", sortBy)
                        .param("asc", String.valueOf(asc))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testUpdateCategoryInStock() throws Exception {
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName("Updated Category");
        categoryRequest.setDescription("Updated Description");


        mockMvc.perform(MockMvcRequestBuilders.put("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isNoContent());
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
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteCategoryFromStock() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/categories/{categoryName}", "Test Category"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteCategoryFromStock_NotFound() throws Exception {
        doThrow(new CategoryNotFoundException()).when(categoryHandler).deleteCategoryInStock(anyString());

        mockMvc.perform(MockMvcRequestBuilders.delete("/categories/{categoryName}", "Test Category"))
                .andExpect(status().isNotFound());
    }
}
