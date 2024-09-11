package bootcamp.emazon.stock.domain.api;

import bootcamp.emazon.stock.domain.model.Article;

public interface IArticleServicePort {

    Article saveArticle(Article article);
}
