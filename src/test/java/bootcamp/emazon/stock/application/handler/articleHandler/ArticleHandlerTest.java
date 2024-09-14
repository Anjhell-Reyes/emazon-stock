package bootcamp.emazon.stock.application.handler.articleHandler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import bootcamp.emazon.stock.application.dto.articleDto.ArticlePaginated;
import bootcamp.emazon.stock.application.dto.articleDto.ArticleRequest;
import bootcamp.emazon.stock.application.dto.articleDto.ArticleResponse;
import bootcamp.emazon.stock.application.mapper.ArticleMapper;
import bootcamp.emazon.stock.domain.api.IArticleServicePort;
import bootcamp.emazon.stock.domain.model.Article;
import bootcamp.emazon.stock.domain.model.CustomPage;

@ExtendWith(MockitoExtension.class)
class ArticleHandlerTest {

    @Mock
    private IArticleServicePort articleServicePort;

    @Mock
    private ArticleMapper articleMapper;

    @InjectMocks
    private ArticleHandler articleHandler;

    @Test
    void testSaveArticleInStock() {
        // Arrange
        ArticleRequest articleRequest = new ArticleRequest();
        Article article = new Article();
        when(articleMapper.toArticle(articleRequest)).thenReturn(article);

        // Act
        articleHandler.saveArticleInStock(articleRequest);

        // Assert
        verify(articleMapper, times(1)).toArticle(articleRequest);
        verify(articleServicePort, times(1)).saveArticle(article);
    }

    @Test
    void testGetArticleFromStock() {
        // Arrange
        String articleName = "iPhone";
        Article article = new Article();
        ArticleResponse articleResponse = new ArticleResponse();
        when(articleServicePort.getArticle(articleName)).thenReturn(article);
        when(articleMapper.toResponse(article)).thenReturn(articleResponse);

        // Act
        ArticleResponse result = articleHandler.getArticleFromStock(articleName);

        // Assert
        assertEquals(articleResponse, result);
        verify(articleServicePort, times(1)).getArticle(articleName);
        verify(articleMapper, times(1)).toResponse(article);
    }

    @Test
    void testGetAllArticlesFromStock() {
        // Arrange
        int page = 1;
        int size = 10;
        String sortBy = "name";
        boolean asc = true;

        // Crea artículos de prueba
        Article article1 = new Article();
        Article article2 = new Article();
        List<Article> articles = Arrays.asList(article1, article2);

        // Crea objetos paginados esperados
        ArticlePaginated articlePaginated1 = new ArticlePaginated();
        ArticlePaginated articlePaginated2 = new ArticlePaginated();
        List<ArticlePaginated> paginatedArticles = Arrays.asList(articlePaginated1, articlePaginated2);

        // Configura los mocks
        CustomPage<Article> customPage = new CustomPage<>(articles, page, size, 2L);
        when(articleServicePort.getAllArticles(page, size, sortBy, asc)).thenReturn(customPage);
        when(articleMapper.toArticlePaginated(article1)).thenReturn(articlePaginated1);
        when(articleMapper.toArticlePaginated(article2)).thenReturn(articlePaginated2);

        // Act
        Page<ArticlePaginated> result = articleHandler.getAllArticlesFromStock(page, size, sortBy, asc);

        // Assert
        assertEquals(paginatedArticles.size(), result.getContent().size());
        assertEquals(12L, result.getTotalElements());
        verify(articleServicePort, times(1)).getAllArticles(page, size, sortBy, asc);
        verify(articleMapper, times(2)).toArticlePaginated(any(Article.class));
        verify(articleMapper, times(2)).toArticlePaginated(any(Article.class));
    }

    @Test
    void testUpdateArticleInStock() {
        // Arrange
        ArticleRequest articleRequest = new ArticleRequest();
        articleRequest.setName("iPhone");
        Article oldArticle = new Article();
        Article newArticle = new Article();
        when(articleServicePort.getArticle(articleRequest.getName())).thenReturn(oldArticle);
        when(articleMapper.toArticle(articleRequest)).thenReturn(newArticle);

        // Act
        articleHandler.updateArticleInStock(articleRequest);

        // Assert
        assertEquals(oldArticle.getId(), newArticle.getId());
        assertEquals(oldArticle.getName(), newArticle.getName());
        assertEquals(oldArticle.getDescription(), newArticle.getDescription());
        assertEquals(oldArticle.getBrand(), newArticle.getBrand());
        assertEquals(oldArticle.getCategories(), newArticle.getCategories());
        assertEquals(oldArticle.getPrice(), newArticle.getPrice());
        assertEquals(oldArticle.getQuantity(), newArticle.getQuantity());
        verify(articleServicePort, times(1)).updateArticle(newArticle);
    }

    @Test
    void testDeleteArticleInStock() {
        // Arrange
        String articleName = "iPhone";

        // Act
        articleHandler.deleteArticleInStock(articleName);

        // Assert
        verify(articleServicePort, times(1)).deleteArticle(articleName);
    }

}