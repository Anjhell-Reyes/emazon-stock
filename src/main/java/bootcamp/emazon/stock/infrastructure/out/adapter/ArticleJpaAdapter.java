package bootcamp.emazon.stock.infrastructure.out.adapter;

import bootcamp.emazon.stock.domain.exception.NoDataFoundException;
import bootcamp.emazon.stock.domain.model.Article;
import bootcamp.emazon.stock.domain.spi.IArticlePersistencePort;
import bootcamp.emazon.stock.domain.exception.ArticleAlreadyExistsException;
import bootcamp.emazon.stock.domain.exception.ArticleNotFoundException;
import bootcamp.emazon.stock.infrastructure.out.entity.ArticleEntity;
import bootcamp.emazon.stock.infrastructure.out.mapper.ArticleEntityMapper;
import bootcamp.emazon.stock.infrastructure.out.mapper.CategoryEntityMapper;
import bootcamp.emazon.stock.infrastructure.out.repository.IArticleRepository;
import bootcamp.emazon.stock.infrastructure.out.repository.ICategoryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
public class ArticleJpaAdapter implements IArticlePersistencePort {

    private final IArticleRepository articleRepository;

    private final ArticleEntityMapper articleEntityMapper;

    private final CategoryEntityMapper categoryEntityMapper;

    private final ICategoryRepository categoryRepository;


    @Override
    public Article saveArticle(Article article){
        if(articleRepository.findByName(article.getName()).isPresent()){
            throw new ArticleAlreadyExistsException();
        }
        articleRepository.save(articleEntityMapper.toEntity(article));
        return article;
    }

    @Override
    public Article getArticle(String articleName) {
        return articleEntityMapper.toArticle(articleRepository.findByName(articleName).orElseThrow(ArticleNotFoundException::new));
    }

    @Override
    public List<Article> getAllArticles(int offset, int limit, String sortBy, boolean asc) {
        Sort sort = Sort.by(asc ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(offset / limit, limit, sort);

        Page<ArticleEntity> articlePage = articleRepository.findAll(pageable);

        if (articlePage.isEmpty()) {
            throw new NoDataFoundException();
        }
        return articlePage.getContent().stream()
                .map(articleEntityMapper::toArticle)
                .collect(Collectors.toList());
    }

    @Override
    public void updateArticle(Article article) {
        articleRepository.save(articleEntityMapper.toEntity(article));
    }

    @Override
    public void deleteArticle(String articleName) {
        articleRepository.deleteByName(articleName);
    }

    @Override
    public long countArticles() {
        return articleRepository.count();
    }
}
