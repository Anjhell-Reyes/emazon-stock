package bootcamp.emazon.stock.application.handler.articleHandler;

import bootcamp.emazon.stock.application.dto.articleDto.ArticleRequest;
import bootcamp.emazon.stock.application.dto.categoryDto.CategoryRequest;

public interface IArticleHandler {

    void saveArticleInStock(ArticleRequest articleRequest);

}
