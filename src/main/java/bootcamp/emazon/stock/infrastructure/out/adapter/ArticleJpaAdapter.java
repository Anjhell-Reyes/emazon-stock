package bootcamp.emazon.stock.infrastructure.out.adapter;

import bootcamp.emazon.stock.domain.model.Article;
import bootcamp.emazon.stock.domain.spi.IArticlePersistencePort;
import bootcamp.emazon.stock.domain.exception.ArticleAlreadyExistsException;
import bootcamp.emazon.stock.infrastructure.out.mapper.ArticleEntityMapper;
import bootcamp.emazon.stock.infrastructure.out.mapper.CategoryEntityMapper;
import bootcamp.emazon.stock.infrastructure.out.repository.IArticleRepository;
import bootcamp.emazon.stock.infrastructure.out.repository.ICategoryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;

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
}
