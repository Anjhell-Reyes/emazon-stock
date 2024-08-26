package bootcamp.emazon.stock.infrastructure.out.mapper;

import bootcamp.emazon.stock.domain.model.Categoria;
import bootcamp.emazon.stock.infrastructure.out.entity.CategoriaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CategoriaEntityMapper {

    CategoriaEntity toEntity(Categoria categoria);

    Categoria toCategoria(CategoriaEntity categoriaEntity);

    List<Categoria> toCategoriaList(List<CategoriaEntity> categoriaEntityList);
}
