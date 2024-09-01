package bootcamp.emazon.stock.infrastructure.input;

import bootcamp.emazon.stock.application.dto.brandDto.BrandRequest;
import bootcamp.emazon.stock.application.dto.brandDto.BrandResponse;
import bootcamp.emazon.stock.application.handler.brandHandler.IBrandHandler;
import bootcamp.emazon.stock.infrastructure.exception.BrandAlreadyExistsException;
import bootcamp.emazon.stock.infrastructure.exception.BrandNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
        doThrow(new BrandAlreadyExistsException()).when(brandHandler).saveBrandInStock(any(BrandRequest.class));

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