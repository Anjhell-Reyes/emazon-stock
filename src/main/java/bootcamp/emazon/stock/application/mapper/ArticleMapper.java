package bootcamp.emazon.stock.application.mapper;

import bootcamp.emazon.stock.application.dto.articleDto.ArticlePaginated;
import bootcamp.emazon.stock.application.dto.articleDto.ArticleRequest;
import bootcamp.emazon.stock.application.dto.articleDto.ArticleResponse;
import bootcamp.emazon.stock.application.dto.brandDto.BrandSummaryResponse;
import bootcamp.emazon.stock.application.dto.categoryDto.CategorySummaryResponse;
import bootcamp.emazon.stock.domain.model.Article;
import bootcamp.emazon.stock.domain.model.Brand;
import bootcamp.emazon.stock.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

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

    @Mapping(source = "brand", target = "brand", qualifiedByName = "toBrandSummaryResponse")
    @Mapping(source = "categories", target = "categories", qualifiedByName = "toCategorySummaryResponses")
    ArticleResponse toResponse(Article article);


    @Mapping(source = "brand", target = "brand", qualifiedByName = "toBrandSummaryResponse")
    @Mapping(source = "categories", target = "categories", qualifiedByName = "toCategorySummaryResponses")
    ArticlePaginated toArticlePaginated(Article article);

    @Named("toBrandSummaryResponse")
    default BrandSummaryResponse toBrandSummaryResponse(Brand brand) {
        return new BrandSummaryResponse(brand.getId(), brand.getName());
    }

    @Named("toCategorySummaryResponses")
    default List<CategorySummaryResponse> toCategorySummaryResponses(List<Category> categories) {
        return categories.stream()
                .map(category -> new CategorySummaryResponse(category.getId(), category.getName()))
                .collect(Collectors.toList());
    }
}
