package bootcamp.emazon.stock.infrastructure.out.adapter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import bootcamp.emazon.stock.domain.exception.ArticleAlreadyExistsException;
import bootcamp.emazon.stock.domain.exception.ArticleNotFoundException;
import bootcamp.emazon.stock.domain.exception.NoDataFoundException;
import bootcamp.emazon.stock.domain.model.Article;
import bootcamp.emazon.stock.domain.model.Brand;
import bootcamp.emazon.stock.domain.model.CustomPage;
import bootcamp.emazon.stock.infrastructure.out.entity.ArticleEntity;
import bootcamp.emazon.stock.infrastructure.out.entity.BrandEntity;
import bootcamp.emazon.stock.infrastructure.out.mapper.ArticleEntityMapper;
import bootcamp.emazon.stock.infrastructure.out.mapper.CategoryEntityMapper;
import bootcamp.emazon.stock.infrastructure.out.repository.IArticleRepository;
import bootcamp.emazon.stock.infrastructure.out.repository.ICategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ArticleJpaAdapterTest {

    @Mock
    private IArticleRepository articleRepository;

    @Mock
    private ArticleEntityMapper articleEntityMapper;

    @Mock
    private CategoryEntityMapper categoryEntityMapper;

    @Mock
    private ICategoryRepository categoryRepository;

    @InjectMocks
    private ArticleJpaAdapter articleJpaAdapter;

    @Test
    void testSaveArticle_Success() {
        Article article = new Article();
        article.setName("Test Article");
        ArticleEntity articleEntity = new ArticleEntity();
        when(articleRepository.findByName(article.getName())).thenReturn(Optional.empty());
        when(articleEntityMapper.toEntity(article)).thenReturn(articleEntity);
        when(articleRepository.save(articleEntity)).thenReturn(articleEntity);

        Article result = articleJpaAdapter.saveArticle(article);

        assertNotNull(result);
        assertEquals(article, result);
        verify(articleRepository, times(1)).findByName(article.getName());
        verify(articleRepository, times(1)).save(articleEntity);
    }

    @Test
    void testSaveArticle_AlreadyExists() {
        Article article = new Article();
        article.setName("Test Article");
        when(articleRepository.findByName(article.getName())).thenReturn(Optional.of(new ArticleEntity()));

        assertThrows(ArticleAlreadyExistsException.class, () -> articleJpaAdapter.saveArticle(article));

        verify(articleRepository, times(1)).findByName(article.getName());
        verify(articleRepository, never()).save(any(ArticleEntity.class));
    }

    @Test
    void testGetArticle_Success() {
        ArticleEntity articleEntity = new ArticleEntity();
        Article article = new Article();
        when(articleRepository.findByName("Test Article")).thenReturn(Optional.of(articleEntity));
        when(articleEntityMapper.toArticle(articleEntity)).thenReturn(article);

        Article result = articleJpaAdapter.getArticle("Test Article");

        assertNotNull(result);
        assertEquals(article, result);
        verify(articleRepository, times(1)).findByName("Test Article");
    }

    @Test
    void testGetArticle_NotFound() {
        when(articleRepository.findByName("Test Article")).thenReturn(Optional.empty());

        assertThrows(ArticleNotFoundException.class, () -> articleJpaAdapter.getArticle("Test Article"));

        verify(articleRepository, times(1)).findByName("Test Article");
    }

    @Test
    public void testGetAllArticles_Success() {
        // Datos de prueba
        ArticleEntity articleEntity1 = new ArticleEntity();
        articleEntity1.setId(1L);
        articleEntity1.setName("Article 1");
        articleEntity1.setDescription("Description 1");
        articleEntity1.setQuantity(10);
        articleEntity1.setPrice(100.0);

        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setId(2L);
        brandEntity.setName("Brand");
        brandEntity.setDescription("Description");

        articleEntity1.setBrand(brandEntity);

        ArticleEntity articleEntity2 = new ArticleEntity();
        articleEntity2.setId(2L);
        articleEntity2.setName("Article 2");
        articleEntity2.setDescription("Description 2");
        articleEntity2.setQuantity(20);
        articleEntity2.setPrice(200.0);

        BrandEntity brandEntity2 = new BrandEntity();
        brandEntity2.setId(2L);
        brandEntity2.setName("Brand 2");
        brandEntity2.setDescription("Description 2");

        articleEntity2.setBrand(brandEntity2);

        List<ArticleEntity> articleEntities = Arrays.asList(articleEntity1, articleEntity2);

        Page<ArticleEntity> articlePage = new PageImpl<>(articleEntities, PageRequest.of(0, 10), 2);

        Article article1 = new Article(1L, "Article 1", "Description 1", 10, 100.0, new Brand(1L, "Brand 1", "Description 1"));
        Article article2 = new Article(2L, "Article 2", "Description 2", 20, 200.0, new Brand(2L, "Brand 2", "Description 2"));

        List<Article> articles = Arrays.asList(article1, article2);

        when(articleRepository.findAll(any(Pageable.class))).thenReturn(articlePage);
        when(articleEntityMapper.toArticle(articleEntity1)).thenReturn(article1);
        when(articleEntityMapper.toArticle(articleEntity2)).thenReturn(article2);

        CustomPage<Article> result = articleJpaAdapter.getAllArticles(0, 10, "name", true);

        assertNotNull(result);
        assertEquals(0, result.getPageNumber());
        assertEquals(10, result.getPageSize());
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(articles, result.getContent());
    }

    @Test
    public void testGetAllArticles_NoDataFound() {
        Page<ArticleEntity> articlePage = new PageImpl<>(List.of(), PageRequest.of(0, 10), 0);

        when(articleRepository.findAll(any(Pageable.class))).thenReturn(articlePage);

        assertThrows(NoDataFoundException.class, () -> articleJpaAdapter.getAllArticles(0, 10, "name", true));
    }

    @Test
    void testUpdateArticle() {
        Article article = new Article();
        ArticleEntity articleEntity = new ArticleEntity();
        when(articleEntityMapper.toEntity(article)).thenReturn(articleEntity);
        when(articleRepository.save(articleEntity)).thenReturn(articleEntity);

        articleJpaAdapter.updateArticle(article);

        verify(articleEntityMapper, times(1)).toEntity(article);
        verify(articleRepository, times(1)).save(articleEntity);
    }

    @Test
    void testDeleteArticle() {
        String articleName = "Test Article";

        articleJpaAdapter.deleteArticle(articleName);

        verify(articleRepository, times(1)).deleteByName(articleName);
    }
}
