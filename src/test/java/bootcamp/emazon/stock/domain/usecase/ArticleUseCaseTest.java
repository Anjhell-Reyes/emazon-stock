package bootcamp.emazon.stock.domain.usecase;

import bootcamp.emazon.stock.domain.exception.AvailableBrandsException;
import bootcamp.emazon.stock.domain.exception.BrandNotFoundException;
import bootcamp.emazon.stock.domain.exception.DuplicateCategoriesException;
import bootcamp.emazon.stock.domain.exception.InvalidNumberOfCategoriesException;
import bootcamp.emazon.stock.domain.model.Article;
import bootcamp.emazon.stock.domain.model.Brand;
import bootcamp.emazon.stock.domain.model.Category;
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
    private Set<Category> categories;
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
}