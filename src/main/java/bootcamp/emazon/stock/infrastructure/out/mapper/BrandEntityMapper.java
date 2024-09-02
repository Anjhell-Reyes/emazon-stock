package bootcamp.emazon.stock.infrastructure.out.mapper;

import bootcamp.emazon.stock.domain.model.Brand;
import bootcamp.emazon.stock.domain.pagination.BrandPaginated;
import bootcamp.emazon.stock.domain.pagination.CategoryPaginated;
import bootcamp.emazon.stock.infrastructure.out.entity.BrandEntity;
import bootcamp.emazon.stock.infrastructure.out.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface BrandEntityMapper {

    BrandEntity toEntity(Brand brand);

    Brand toBrand(BrandEntity brandEntity);

    BrandPaginated toBrandPaginated(BrandEntity brandEntity);

}
