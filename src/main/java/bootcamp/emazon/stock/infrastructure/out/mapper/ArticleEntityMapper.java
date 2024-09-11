package bootcamp.emazon.stock.infrastructure.out.mapper;

import bootcamp.emazon.stock.domain.model.Article;
import bootcamp.emazon.stock.infrastructure.out.entity.ArticleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ArticleEntityMapper {

    ArticleEntity toEntity(Article article);

    Article toArticle(ArticleEntity articleEntity);
}
