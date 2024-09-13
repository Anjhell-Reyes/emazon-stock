package bootcamp.emazon.stock.infrastructure.out.mapper;

import bootcamp.emazon.stock.domain.model.Brand;
import bootcamp.emazon.stock.application.dto.brandDto.BrandPaginated;
import bootcamp.emazon.stock.infrastructure.out.entity.BrandEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface BrandEntityMapper {

    BrandEntity toEntity(Brand brand);

    Brand toBrand(BrandEntity brandEntity);

    BrandPaginated toBrandPaginated(BrandEntity brandEntity);

}
