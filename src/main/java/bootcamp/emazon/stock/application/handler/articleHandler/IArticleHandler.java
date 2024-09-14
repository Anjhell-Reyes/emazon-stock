package bootcamp.emazon.stock.application.handler.articleHandler;

import bootcamp.emazon.stock.application.dto.articleDto.ArticlePaginated;
import bootcamp.emazon.stock.application.dto.articleDto.ArticleRequest;
import bootcamp.emazon.stock.application.dto.articleDto.ArticleResponse;
import bootcamp.emazon.stock.application.dto.categoryDto.CategoryRequest;
import org.springframework.data.domain.Page;

public interface IArticleHandler {

    void saveArticleInStock(ArticleRequest articleRequest);

    ArticleResponse getArticleFromStock(String articleName);

    void updateArticleInStock(ArticleRequest articleRequest);

    void deleteArticleInStock(String articleName);

    Page<ArticlePaginated> getAllArticlesFromStock(int page, int size, String sortBy, boolean asc);

}
