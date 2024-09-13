package bootcamp.emazon.stock.infrastructure.input;

import bootcamp.emazon.stock.application.dto.brandDto.BrandRequest;
import bootcamp.emazon.stock.application.dto.brandDto.BrandResponse;
import bootcamp.emazon.stock.application.handler.brandHandler.IBrandHandler;
import bootcamp.emazon.stock.domain.exception.DescriptionMax120CharactersException;
import bootcamp.emazon.stock.application.dto.brandDto.BrandPaginated;
import bootcamp.emazon.stock.domain.exception.BrandNotFoundException;
import bootcamp.emazon.stock.domain.exception.NoDataFoundException;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BrandRestController.class)
class BrandRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IBrandHandler brandHandler;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void testSaveBrandInStock() throws Exception {
        BrandRequest brandRequest = new BrandRequest();
        brandRequest.setName("Test Brand");
        brandRequest.setDescription("Test Description");

        mockMvc.perform(MockMvcRequestBuilders.post("/brands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(brandRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void testSaveBrandInStock_Conflict() throws Exception {
        BrandRequest brandRequest = new BrandRequest();
        brandRequest.setName("Test Brand");
        brandRequest.setDescription("Test Description");
        doThrow(new DescriptionMax120CharactersException.BrandAlreadyExistsException()).when(brandHandler).saveBrandInStock(any(BrandRequest.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/brands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(brandRequest)))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    void testGetBrandFromStock() throws Exception {
        BrandResponse brandResponse = new BrandResponse();
        brandResponse.setId(1L);
        brandResponse.setName("Test Brand");
        brandResponse.setDescription("Test Description");
        when(brandHandler.getBrandFromStock(anyString())).thenReturn(brandResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/brands/{brandName}", "Test Brand")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Brand"))
                .andExpect(jsonPath("$.description").value("Test Description"));
    }

    @Test
    void testGetBrandFromStock_NotFound() throws Exception {
        when(brandHandler.getBrandFromStock(anyString())).thenThrow(new BrandNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("/brands/{brandName}", "Test Brand")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testGetBrandsFromStock_NotFound() throws Exception {
        when(brandHandler.getAllBrandsFromStock(anyInt(), anyInt(), anyString(), anyBoolean())).thenThrow(new NoDataFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("/brands")
                        .param("page", "1")
                        .param("size", "10")
                        .param("sortBy", "name")
                        .param("asc", "true")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testGetBrandsFromStockWithResults() throws Exception {
        // Arrange
        int page = 1;
        int size = 10;
        String sortBy = "name";
        boolean asc = true;

        BrandPaginated brandPaginated = new BrandPaginated(); // Crea un objeto de ejemplo de BrandPaginated
        Page<BrandPaginated> paginatedBrands = new PageImpl<>(List.of(brandPaginated), PageRequest.of(page - 1, size), 1);

        when(brandHandler.getAllBrandsFromStock(page, size, sortBy, asc))
                .thenReturn(paginatedBrands);

        // Act & Assert
        mockMvc.perform(get("/brands") // Cambia "/categories" a "/brands"
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("sortBy", sortBy)
                        .param("asc", String.valueOf(asc))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetBrandsFromStockNoResults() throws Exception {
        // Arrange
        int page = 1;
        int size = 10;
        String sortBy = "name";
        boolean asc = true;

        Page<BrandPaginated> paginatedBrands = new PageImpl<>(Collections.emptyList(), PageRequest.of(page - 1, size), 0);

        when(brandHandler.getAllBrandsFromStock(page, size, sortBy, asc))
                .thenReturn(paginatedBrands);

        // Act & Assert
        mockMvc.perform(get("/brands") // Cambia "/categories" a "/brands"
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("sortBy", sortBy)
                        .param("asc", String.valueOf(asc))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    void testUpdateBrandInStock() throws Exception {
        BrandRequest brandRequest = new BrandRequest();
        brandRequest.setName("Updated Brand");
        brandRequest.setDescription("Updated Description");

        mockMvc.perform(MockMvcRequestBuilders.put("/brands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(brandRequest)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testUpdateBrandInStock_NotFound() throws Exception {
        BrandRequest brandRequest = new BrandRequest();
        brandRequest.setName("Updated Brand");
        brandRequest.setDescription("Updated Description");
        doThrow(new BrandNotFoundException()).when(brandHandler).updateBrandInStock(any(BrandRequest.class));

        mockMvc.perform(MockMvcRequestBuilders.put("/brands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(brandRequest)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testDeleteBrandFromStock() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/brands/{brandName}", "Test Brand"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testDeleteBrandFromStock_NotFound() throws Exception {
        doThrow(new BrandNotFoundException()).when(brandHandler).deleteBrandInStock(anyString());

        mockMvc.perform(MockMvcRequestBuilders.delete("/brands/{brandName}", "Test Brand"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}