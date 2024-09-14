package bootcamp.emazon.stock.infrastructure.input;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import bootcamp.emazon.stock.application.dto.articleDto.ArticlePaginated;
import bootcamp.emazon.stock.application.dto.articleDto.ArticleRequest;
import bootcamp.emazon.stock.application.dto.articleDto.ArticleResponse;
import bootcamp.emazon.stock.application.handler.articleHandler.IArticleHandler;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

@WebMvcTest(controllers = ArticleRestController.class)
class ArticleRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IArticleHandler articleHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSaveArticleInStock() throws Exception {
        ArticleRequest articleRequest = new ArticleRequest();
        articleRequest.setName("iPhone");
        articleRequest.setDescription("Latest model");
        // Add other fields if needed

        mockMvc.perform(MockMvcRequestBuilders.post("/articles")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(articleRequest)))
                .andExpect(status().isCreated());

        Mockito.verify(articleHandler, Mockito.times(1)).saveArticleInStock(articleRequest);
    }

    @Test
    void testGetArticleFromStock() throws Exception {
        String articleName = "iPhone";
        ArticleResponse articleResponse = new ArticleResponse();
        // Set fields on articleResponse as needed

        Mockito.when(articleHandler.getArticleFromStock(articleName)).thenReturn(articleResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/articles/{articleName}", articleName))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(articleResponse)));

        Mockito.verify(articleHandler, Mockito.times(1)).getArticleFromStock(articleName);
    }

    @Test
    void testGetArticlesFromStock() throws Exception {
        int page = 1;
        int size = 10;
        String sortBy = "name";
        boolean asc = true;
        ArticlePaginated articlePaginated = new ArticlePaginated();
        // Add fields if needed

        Mockito.when(articleHandler.getAllArticlesFromStock(page, size, sortBy, asc))
                .thenReturn(new PageImpl<>(List.of(articlePaginated), PageRequest.of(page - 1, size), 1L));

        mockMvc.perform(MockMvcRequestBuilders.get("/articles")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("sortBy", sortBy)
                        .param("asc", String.valueOf(asc)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0]").exists());

        Mockito.verify(articleHandler, Mockito.times(1))
                .getAllArticlesFromStock(page, size, sortBy, asc);
    }

    @Test
    void testUpdateArticleInStock() throws Exception {
        ArticleRequest articleRequest = new ArticleRequest();
        articleRequest.setName("iPhone");
        articleRequest.setDescription("Updated description");
        // Add other fields if needed

        mockMvc.perform(MockMvcRequestBuilders.put("/articles")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(articleRequest)))
                .andExpect(status().isNoContent());

        Mockito.verify(articleHandler, Mockito.times(1)).updateArticleInStock(articleRequest);
    }

    @Test
    void testDeleteArticleFromStock() throws Exception {
        String articleName = "iPhone";

        mockMvc.perform(MockMvcRequestBuilders.delete("/articles/{articleName}", articleName))
                .andExpect(status().isNoContent());

        Mockito.verify(articleHandler, Mockito.times(1)).deleteArticleInStock(articleName);
    }
}
