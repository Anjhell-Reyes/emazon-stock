package bootcamp.emazon.stock.application.mapper;

import bootcamp.emazon.stock.application.dto.categoriaDto.CategoriaRequest;
import bootcamp.emazon.stock.application.dto.categoriaDto.CategoriaResponse;
import bootcamp.emazon.stock.domain.model.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CategoriaMapper {

    Categoria toCategoria(CategoriaRequest categoriaRequest);
    CategoriaResponse toResponse(Categoria categoria);

    default List<CategoriaResponse> toResponseList(List<Categoria> categoriaList) {
        return categoriaList.stream()
                .map(categoria -> {
                    CategoriaResponse categoriaResponse = new CategoriaResponse();
                    categoriaResponse.setId(categoria.getId());
                    categoriaResponse.setNombre(categoria.getNombre());
                    categoriaResponse.setDescripcion(categoria.getDescripcion());
                    return categoriaResponse;
                }).toList();
    }
}
