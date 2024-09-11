package bootcamp.emazon.stock.application.handler.articleHandler;

import bootcamp.emazon.stock.application.dto.articleDto.ArticleRequest;
import bootcamp.emazon.stock.application.mapper.ArticleMapper;
import bootcamp.emazon.stock.domain.api.IArticleServicePort;
import bootcamp.emazon.stock.domain.model.Article;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleHandler implements  IArticleHandler{

    private final IArticleServicePort articleServicePort;
    private final ArticleMapper articleMapper;

    public void saveArticleInStock(ArticleRequest articleRequest){
        Article article = articleMapper.toArticle(articleRequest);
        articleServicePort.saveArticle(article);
    }
}
