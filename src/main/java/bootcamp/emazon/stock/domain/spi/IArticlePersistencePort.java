package bootcamp.emazon.stock.domain.spi;

import bootcamp.emazon.stock.domain.model.Article;
import bootcamp.emazon.stock.domain.model.CustomPage;

public interface IArticlePersistencePort {

    Article saveArticle(Article article);

    Article getArticle(String articleName);

    CustomPage<Article> getAllArticles(int offset, int limit, String sortBy, boolean asc);

    void updateArticle(Article article);
    void deleteArticle(String articleName);
}
