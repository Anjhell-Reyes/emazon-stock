package bootcamp.emazon.stock.domain.spi;

import bootcamp.emazon.stock.domain.model.Article;

public interface IArticlePersistencePort {

    Article saveArticle(Article article);
}
