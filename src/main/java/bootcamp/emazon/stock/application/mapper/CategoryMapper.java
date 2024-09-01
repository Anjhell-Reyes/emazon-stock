package bootcamp.emazon.stock.application.mapper;

import bootcamp.emazon.stock.application.dto.categoriaDto.CategoryRequest;
import bootcamp.emazon.stock.application.dto.categoriaDto.CategoryResponse;
import bootcamp.emazon.stock.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    Category toCategory(CategoryRequest categoryRequest);
    CategoryResponse toResponse(Category category);

}
