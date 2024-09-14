package bootcamp.emazon.stock.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class ArticleTest {

    @Test
    void testDefaultConstructor() {
        Article article = new Article();
        assertNotNull(article);
        assertNull(article.getId());
        assertNull(article.getName());
        assertNull(article.getDescription());
        assertNull(article.getQuantity());
        assertEquals(0.0, article.getPrice());
        assertNull(article.getBrand());
        assertNull(article.getCategories());
    }

    @Test
    void testAllFieldsConstructor() {
        Brand brand = new Brand();
        Article article = new Article(1L, "Article Name", "Article Description", 10, 99.99, brand);
        assertEquals(1L, article.getId());
        assertEquals("Article Name", article.getName());
        assertEquals("Article Description", article.getDescription());
        assertEquals(10, article.getQuantity());
        assertEquals(99.99, article.getPrice());
        assertEquals(brand, article.getBrand());
        assertNull(article.getCategories());
    }

    @Test
    void testAllFieldsConstructorWithCategories() {
        Brand brand = new Brand();
        Category category1 = new Category();
        Category category2 = new Category();
        List<Category> categories = Arrays.asList(category1, category2);
        Article article = new Article(1L, "Article Name", "Article Description", 10, 99.99, brand, categories);
        assertEquals(1L, article.getId());
        assertEquals("Article Name", article.getName());
        assertEquals("Article Description", article.getDescription());
        assertEquals(10, article.getQuantity());
        assertEquals(99.99, article.getPrice());
        assertEquals(brand, article.getBrand());
        assertEquals(categories, article.getCategories());
    }

    @Test
    void testConstructorWithoutBrand() {
        Article article = new Article(1L, "Article Name", "Article Description", 10, 99.99);
        assertEquals(1L, article.getId());
        assertEquals("Article Name", article.getName());
        assertEquals("Article Description", article.getDescription());
        assertEquals(10, article.getQuantity());
        assertEquals(99.99, article.getPrice());
        assertNull(article.getBrand());
        assertNull(article.getCategories());
    }

    @Test
    void testSettersAndGetters() {
        Article article = new Article();
        Brand brand = new Brand();
        Category category = new Category();
        List<Category> categories = Arrays.asList(category);

        article.setId(1L);
        article.setName("Article Name");
        article.setDescription("Article Description");
        article.setQuantity(10);
        article.setPrice(99.99);
        article.setBrand(brand);
        article.setCategories(categories);

        assertEquals(1L, article.getId());
        assertEquals("Article Name", article.getName());
        assertEquals("Article Description", article.getDescription());
        assertEquals(10, article.getQuantity());
        assertEquals(99.99, article.getPrice());
        assertEquals(brand, article.getBrand());
        assertEquals(categories, article.getCategories());
    }
}
