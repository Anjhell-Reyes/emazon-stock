package bootcamp.emazon.stock.domain.usecase;

import bootcamp.emazon.stock.domain.model.Article;
import bootcamp.emazon.stock.domain.model.Brand;
import bootcamp.emazon.stock.domain.model.Category;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArticleUseCase {

    public Article saveArticle(Long id, String name, String description, Integer quantity, Double price, Brand brand, List<Category> categories) {
        // Validación
        validateCategories(categories);

        // Crear Articulo
        Article article = new Article(id, name, description, quantity, price, brand);
        article.getCategories().addAll(categories);

        // Puedes añadir lógica adicional aquí, como guardar el artículo en un repositorio

        return article;
    }

    private void validateCategories(List<Category> categories) {
        if (categories == null || categories.size() < 1 || categories.size() > 3) {
            throw new IllegalArgumentException("El artículo debe tener entre 1 y 3 categorías");
        }

        Set<Category> uniqueCategories = new HashSet<>(categories);
        if (uniqueCategories.size() != categories.size()) {
            throw new IllegalArgumentException("El artículo no puede tener categorías repetidas");
        }
    }
}
