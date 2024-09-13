package bootcamp.emazon.stock.infrastructure.out.mapper;

import bootcamp.emazon.stock.domain.model.Category;
import bootcamp.emazon.stock.application.dto.categoryDto.CategoryPaginated;
import bootcamp.emazon.stock.infrastructure.out.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CategoryEntityMapper {

    CategoryEntity toEntity(Category category);

    Category toCategory(CategoryEntity categoryEntity);

    CategoryPaginated toCategoryPaginated(CategoryEntity entity);
}
