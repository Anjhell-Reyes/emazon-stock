package bootcamp.emazon.stock.domain.spi;

import bootcamp.emazon.stock.domain.model.Article;

import java.util.List;

public interface IArticlePersistencePort {

    Article saveArticle(Article article);

    Article getArticle(String articleName);

    List<Article> getAllArticles(int offset, int limit, String sortBy, boolean asc);

    void updateArticle(Article article);
    void deleteArticle(String articleName);

    long countArticles();
}
