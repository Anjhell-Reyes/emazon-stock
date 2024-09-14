package bootcamp.emazon.stock.application.mapper;

import bootcamp.emazon.stock.application.dto.articleDto.ArticlePaginated;
import bootcamp.emazon.stock.application.dto.articleDto.ArticleRequest;
import bootcamp.emazon.stock.application.dto.articleDto.ArticleResponse;
import bootcamp.emazon.stock.domain.model.Article;
import bootcamp.emazon.stock.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    @Mapping(source = "brandName", target = "brand.name")
    @Mapping(source = "categories", target = "categories", qualifiedByName = "toCategoryList")
    Article toArticle(ArticleRequest articleRequest);

    @Named("toCategoryList")
    default List<Category> toCategoryList(List<String> categoryNames) {
        return categoryNames.stream()
                .map(name -> new Category(null, name, "Default description"))
                .toList();
    }

    @Mapping(source = "brand.name", target = "brandName")
    @Mapping(source = "categories", target = "categoryNames", qualifiedByName = "toCategoryNames")
    ArticleResponse toResponse(Article article);


    @Mapping(source = "brand.name", target = "brandName")
    @Mapping(source = "categories", target = "categoryNames", qualifiedByName = "toCategoryNames")
    ArticlePaginated toArticlePaginated(Article article);

    @Named("toCategoryNames")
    default List<String> toCategoryNames(List<Category> categories) {
        return categories.stream()
                .map(Category::getName)
                .toList();
    }
}
