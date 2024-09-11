package bootcamp.emazon.stock.infrastructure.configuration;

import bootcamp.emazon.stock.domain.api.IArticleServicePort;
import bootcamp.emazon.stock.domain.api.IBrandServicePort;
import bootcamp.emazon.stock.domain.api.ICategoryServicePort;
import bootcamp.emazon.stock.domain.spi.IArticlePersistencePort;
import bootcamp.emazon.stock.domain.spi.IBrandPersistencePort;
import bootcamp.emazon.stock.domain.spi.ICategoryPersistencePort;
import bootcamp.emazon.stock.domain.usecase.ArticleUseCase;
import bootcamp.emazon.stock.domain.usecase.BrandUseCase;
import bootcamp.emazon.stock.domain.usecase.CategoryUseCase;
import bootcamp.emazon.stock.infrastructure.out.adapter.ArticleJpaAdapter;
import bootcamp.emazon.stock.infrastructure.out.adapter.BrandJpaAdapter;
import bootcamp.emazon.stock.infrastructure.out.adapter.CategoryJpaAdapter;
import bootcamp.emazon.stock.infrastructure.out.mapper.ArticleEntityMapper;
import bootcamp.emazon.stock.infrastructure.out.mapper.BrandEntityMapper;
import bootcamp.emazon.stock.infrastructure.out.mapper.CategoryEntityMapper;
import bootcamp.emazon.stock.infrastructure.out.repository.IArticleRepository;
import bootcamp.emazon.stock.infrastructure.out.repository.IBrandRepository;
import bootcamp.emazon.stock.infrastructure.out.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final ICategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;
    private final IBrandRepository brandRepository;
    private final BrandEntityMapper brandEntityMapper;
    private final IArticleRepository articleRepository;
    private final ArticleEntityMapper articleEntityMapper;

    @Bean
    public ICategoryPersistencePort categoryPersistencePort(){
        return new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort() {
        return new CategoryUseCase(categoryPersistencePort());
    }

    @Bean
    public IBrandPersistencePort brandPersistencePort(){
        return new BrandJpaAdapter(brandRepository, brandEntityMapper);
    }

    @Bean
    public IBrandServicePort brandServicePort() {
        return new BrandUseCase(brandPersistencePort());
    }

    @Bean
    public IArticlePersistencePort articlePersistencePort() {
        return new ArticleJpaAdapter(articleRepository, articleEntityMapper,categoryEntityMapper, categoryRepository);
    }

    @Bean
    public IArticleServicePort articleServicePort() {
        return new ArticleUseCase(articlePersistencePort(),brandPersistencePort(),categoryPersistencePort());
    }
}
