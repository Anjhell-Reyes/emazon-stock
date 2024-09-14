package bootcamp.emazon.stock.domain.api;

import bootcamp.emazon.stock.domain.model.Article;
import bootcamp.emazon.stock.domain.model.CustomPage;

public interface IArticleServicePort {

    Article saveArticle(Article article);

    Article getArticle(String articleName);

    CustomPage<Article> getAllArticles(int page, int size, String sortBy, boolean asc);

    void updateArticle(Article article);
    void deleteArticle(String articleName);
}
