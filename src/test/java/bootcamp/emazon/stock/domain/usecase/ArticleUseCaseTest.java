package bootcamp.emazon.stock.domain.usecase;

import bootcamp.emazon.stock.domain.exception.*;
import bootcamp.emazon.stock.domain.model.Article;
import bootcamp.emazon.stock.domain.model.Brand;
import bootcamp.emazon.stock.domain.model.Category;
import bootcamp.emazon.stock.domain.model.CustomPage;
import bootcamp.emazon.stock.domain.spi.IArticlePersistencePort;
import bootcamp.emazon.stock.domain.spi.IBrandPersistencePort;
import bootcamp.emazon.stock.domain.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticleUseCaseTest {

    @Mock
    private IArticlePersistencePort articlePersistencePort;

    @Mock
    private IBrandPersistencePort brandPersistencePort;

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private ArticleUseCase articleUseCase;

    private Brand brand;
    private Category category1;
    private Category category2;
    private Article article;

    @BeforeEach
    void setUp() {
        brand = new Brand(1L, "Apple", "Electronics and Gadgets");
        category1 = new Category(1L, "Electronics", "Devices");
        category2 = new Category(2L, "Gadgets", "Small devices");

        article = new Article(1L, "iPhone", "Latest iPhone model", 5, 999.99);

    }

    @Test
    void saveArticle_ShouldPassValidation_WhenValidArticle() {
        // Arrange
        article.setCategories(Collections.singletonList(category1));
        article.setBrand(brand);

        when(categoryPersistencePort.getCategory(anyString())).thenReturn(category1);
        when(brandPersistencePort.getBrand(anyString())).thenReturn(brand);
        when(articlePersistencePort.saveArticle(any(Article.class))).thenReturn(article);

        // Act
        Article savedArticle = articleUseCase.saveArticle(article);

        // Assert
        verify(articlePersistencePort).saveArticle(article);
        assertEquals(article, savedArticle);
    }

    @Test
    void validateCategories_ShouldThrowInvalidNumberOfCategoriesException_WhenCategoriesExceedLimit() {
        // Arrange
        article.setCategories(List.of(category1, category2,
                new Category(3L, "Category3", "Description"),
                new Category(4L, "Category4", "Description")));

        // Act & Assert
        assertThrows(InvalidNumberOfCategoriesException.class, () -> articleUseCase.validateCategories(article));
    }

    @Test
    void validateCategories_ShouldThrowDuplicateCategoriesException_WhenCategoriesAreDuplicate() {
        // Arrange
        Category categoryDuplicate = new Category(1L, "Electronics", "Devices");
        List<Category> duplicateCategories = Arrays.asList(category1, categoryDuplicate);

        article.setCategories(duplicateCategories);

        // Act & Assert
        assertThrows(DuplicateCategoriesException.class, () -> articleUseCase.validateCategories(article));
    }

    @Test
    void validateBrands_ShouldThrowAvailableBrandsException_WhenBrandNameIsEmpty() {
        // Arrange
        Brand brandEmpty = new Brand();
        brandEmpty.setName("");
        article.setBrand(brandEmpty);

        // Act & Assert
        assertThrows(AvailableBrandsException.class, () -> articleUseCase.validateBrands(article));
    }

    @Test
    void validateBrands_ShouldThrowAvailableBrandsException_WhenBrandNotFound() {
        // Arrange
        Brand brandEmpty = new Brand();
        brandEmpty.setName("");
        article.setBrand(brandEmpty);

        lenient().when(brandPersistencePort.getBrand(anyString())).thenThrow(BrandNotFoundException.class);

        assertThrows(AvailableBrandsException.class, () -> articleUseCase.validateBrands(article));
    }

    @Test
    void validateBrands_ShouldSetBrand_WhenBrandExists() {
        // Arrange
        article.setBrand(brand);

        when(brandPersistencePort.getBrand(anyString())).thenReturn(brand);

        // Act
        articleUseCase.validateBrands(article);

        // Assert
        verify(brandPersistencePort).getBrand(brand.getName());
        assertEquals(brand, article.getBrand());
    }

    @Test
    void getAvailableBrands_ShouldReturnListOfBrands() {
        // Arrange
        Brand brand2 = new Brand(2L, "Nike", "Leading global brand in sportswear and athletic equipment");
        List<Brand> brandList = List.of(brand, brand2);

        when(brandPersistencePort.getAll()).thenReturn(brandList);

        // Act
        List<String> availableBrands = articleUseCase.getAvailableBrands();

        // Assert
        assertTrue(availableBrands.contains("Apple"));
        assertTrue(availableBrands.contains("Nike"));
    }
    @Test
    void testGetArticleSuccess() {
        // Arrange
        String articleName = "iPhone";
        Article expectedArticle = new Article();
        expectedArticle.setName(articleName);
        when(articlePersistencePort.getArticle(articleName)).thenReturn(expectedArticle);

        // Act
        Article result = articleUseCase.getArticle(articleName);

        // Assert
        assertEquals(expectedArticle, result);
        verify(articlePersistencePort, times(1)).getArticle(articleName);
    }

    @Test
    void testGetAllArticlesSuccess() {
        // Arrange
        int page = 1;
        int size = 10;
        String sortBy = "name";
        boolean asc = true;
        int offset = (page - 1) * size;

        List<Article> articles = Arrays.asList(new Article(), new Article());
        when(articlePersistencePort.getAllArticles(offset, size, sortBy, asc)).thenReturn(articles);
        when(articlePersistencePort.countArticles()).thenReturn(2L);

        // Act
        CustomPage<Article> result = articleUseCase.getAllArticles(page, size, sortBy, asc);

        // Assert
        assertEquals(articles, result.getContent());
        assertEquals(2L, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        verify(articlePersistencePort, times(1)).getAllArticles(offset, size, sortBy, asc);
        verify(articlePersistencePort, times(1)).countArticles();
    }

    @Test
    void testGetAllArticlesInvalidPage() {
        // Arrange
        int page = -1;
        int size = 10;
        String sortBy = "name";
        boolean asc = true;

        // Act & Assert
        assertThrows(InvalidPageIndexException.class, () -> articleUseCase.getAllArticles(page, size, sortBy, asc));
    }

    @Test
    void testUpdateArticleSuccess() {

        doNothing().when(articlePersistencePort).updateArticle(any(Article.class));

        // Act
        articleUseCase.updateArticle(article);

        // Assert
        verify(articlePersistencePort, times(1)).updateArticle(any(Article.class));
    }

    @Test
    void testUpdateArticleNameNull() {
        // Arrange
        Article article2 = new Article();
        article.setDescription("New iPhone model");

        // Act & Assert
        assertThrows(NamenotnullException.class, () -> articleUseCase.updateArticle(article2));
    }

    @Test
    void testUpdateArticleDescriptionNull() {
        // Arrange
        Article article2 = new Article();
        article2.setId(1L);
        article2.setName("iphone");
        article2.setPrice(999.99);
        article2.setQuantity(5);

        // Act & Assert
        assertThrows(DescriptionNotnullException.class, () -> articleUseCase.updateArticle(article2));
    }

    @Test
    void testDeleteArticleSuccess() {
        // Arrange
        String articleName = "iPhone";

        // Act
        articleUseCase.deleteArticle(articleName);

        // Assert
        verify(articlePersistencePort, times(1)).deleteArticle(articleName);
    }

    @Test
    void testGetSortByFieldDefault() {
        // Arrange
        String sortBy = "invalidField";

        // Act
        String result = articleUseCase.getSortByField(sortBy);

        // Assert
        assertEquals("name", result);
    }

    @Test
    void testGetSortByFieldBrandName() {
        // Act
        String result = articleUseCase.getSortByField("brandName");

        // Assert
        assertEquals("brand.name", result);
    }

    @Test
    void testGetSortByFieldCategoryNames() {
        // Act
        String result = articleUseCase.getSortByField("categoryNames");

        // Assert
        assertEquals("categories.name", result);
    }
}