package bootcamp.emazon.stock.application.handler.articleHandler;

import bootcamp.emazon.stock.application.dto.articleDto.ArticlePaginated;
import bootcamp.emazon.stock.application.dto.articleDto.ArticleRequest;
import bootcamp.emazon.stock.application.dto.articleDto.ArticleResponse;
import bootcamp.emazon.stock.application.dto.articleDto.QuantityRequest;
import bootcamp.emazon.stock.application.mapper.ArticleMapper;
import bootcamp.emazon.stock.domain.api.IArticleServicePort;
import bootcamp.emazon.stock.domain.model.Article;
import bootcamp.emazon.stock.domain.model.CustomPage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleHandler implements  IArticleHandler{

    private final IArticleServicePort articleServicePort;
    private final ArticleMapper articleMapper;

    @Override
    public void saveArticleInStock(ArticleRequest articleRequest){
        Article article = articleMapper.toArticle(articleRequest);
        articleServicePort.saveArticle(article);
    }

    @Override
    public ArticleResponse getArticleFromStock(String articleName){
        Article article = articleServicePort.getArticle(articleName);
        return articleMapper.toResponse(article);
    }

    @Override
    public Page<ArticlePaginated> getAllArticlesFromStock(int page, int size, String sortBy, boolean asc) {
        CustomPage<Article> customPage = articleServicePort.getAllArticles(page, size, sortBy, asc);

        List<ArticlePaginated> paginatedArticles = customPage.getContent().stream()
                .map(articleMapper::toArticlePaginated)
                .collect(Collectors.toList());

        return new PageImpl<>(paginatedArticles, PageRequest.of(customPage.getPageNumber(), customPage.getPageSize()), customPage.getTotalElements());
    }

    @Override
    public void updateArticleQuantityInStock(String articleName, QuantityRequest quantityRequest) {
        Article article = articleServicePort.getArticle(articleName);

        article.setQuantity(article.getQuantity() + quantityRequest.getQuantityToAdd());
        articleServicePort.updateArticle(article);
    }

    @Override
    public void updateArticleInStock(ArticleRequest articleRequest){
        Article oldArticle = articleServicePort.getArticle(articleRequest.getName());
        Article newArticle = articleMapper.toArticle(articleRequest);
        newArticle.setId(oldArticle.getId());
        newArticle.setName(oldArticle.getName());
        newArticle.setDescription(oldArticle.getDescription());
        newArticle.setBrand(oldArticle.getBrand());
        newArticle.setCategories(oldArticle.getCategories());
        newArticle.setPrice(oldArticle.getPrice());
        newArticle.setQuantity(oldArticle.getQuantity());
        articleServicePort.updateArticle(newArticle);
    }

    @Override
    public void deleteArticleInStock(String articleName){
        articleServicePort.deleteArticle(articleName);
    }

}
