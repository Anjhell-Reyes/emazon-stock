package bootcamp.emazon.stock.infrastructure.out.adapter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import bootcamp.emazon.stock.domain.exception.ArticleAlreadyExistsException;
import bootcamp.emazon.stock.domain.exception.ArticleNotFoundException;
import bootcamp.emazon.stock.domain.exception.NoDataFoundException;
import bootcamp.emazon.stock.domain.model.Article;
import bootcamp.emazon.stock.infrastructure.out.entity.ArticleEntity;
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
    void testGetAllArticles_Success() {
        ArticleEntity articleEntity = new ArticleEntity();
        Article article = new Article();
        List<ArticleEntity> articleEntities = List.of(articleEntity);
        List<Article> articles = List.of(article);
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name"));
        Page<ArticleEntity> articlePage = new PageImpl<>(articleEntities, pageable, 1);
        when(articleRepository.findAll(pageable)).thenReturn(articlePage);
        when(articleEntityMapper.toArticle(articleEntity)).thenReturn(article);

        List<Article> result = articleJpaAdapter.getAllArticles(0, 10, "name", true);

        assertNotNull(result);
        assertEquals(articles, result);
        verify(articleRepository, times(1)).findAll(pageable);
    }

    @Test
    void testGetAllArticles_NoData() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name"));
        when(articleRepository.findAll(pageable)).thenReturn(Page.empty());

        assertThrows(NoDataFoundException.class, () -> articleJpaAdapter.getAllArticles(0, 10, "name", true));

        verify(articleRepository, times(1)).findAll(pageable);
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

    @Test
    void testCountArticles() {
        long count = 5L;
        when(articleRepository.count()).thenReturn(count);

        long result = articleJpaAdapter.countArticles();

        assertEquals(count, result);
        verify(articleRepository, times(1)).count();
    }
}
