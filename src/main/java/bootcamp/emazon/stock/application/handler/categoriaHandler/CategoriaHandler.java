package bootcamp.emazon.stock.application.handler.categoriaHandler;
import bootcamp.emazon.stock.application.dto.categoriaDto.CategoriaRequest;
import bootcamp.emazon.stock.application.dto.categoriaDto.CategoriaResponse;
import bootcamp.emazon.stock.application.mapper.CategoriaMapper;
import bootcamp.emazon.stock.domain.api.ICategoriaServicePort;
import bootcamp.emazon.stock.domain.model.Categoria;
import bootcamp.emazon.stock.application.exception.DescriptionNotnullOrMax90Characters;
import bootcamp.emazon.stock.application.exception.NamenotnullOrMax50Characters;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoriaHandler implements  ICategoriaHandler{

    private final ICategoriaServicePort categoriaServicePort;
    private final CategoriaMapper categoriaMapper;

    @Override
    public void saveCategoriaInStock(CategoriaRequest categoriaRequest){
        if (categoriaRequest.getNombre() == null || categoriaRequest.getNombre().length() > 50) {
            throw new NamenotnullOrMax50Characters();
        }
        if (categoriaRequest.getDescripcion() == null || categoriaRequest.getDescripcion().isEmpty() || categoriaRequest.getDescripcion().length() > 90) {
            throw new DescriptionNotnullOrMax90Characters();
        }
        Categoria categoria = categoriaMapper.toCategoria(categoriaRequest);
        categoriaServicePort.saveCategoria(categoria);
    }

    @Override
    public CategoriaResponse getCategoriaFromStock(String categoriaNombre){
        Categoria categoria = categoriaServicePort.getCategoria(categoriaNombre);
        return categoriaMapper.toResponse(categoria);
    }

    @Override
    public Page<CategoriaResponse> getAllCategoriasFromStock(int page, int size, String sort, String direction) {
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        Page<Categoria> categoriaPage = categoriaServicePort.getAllCategorias(pageable);
        return categoriaPage.map(categoriaMapper::toResponse);
    }

    @Override
    public void updateCategoriaInStock(CategoriaRequest categoriaRequest){
        Categoria oldCategoria = categoriaServicePort.getCategoria(categoriaRequest.getNombre());
        Categoria newCategoria = categoriaMapper.toCategoria(categoriaRequest);
        newCategoria.setId(oldCategoria.getId());
        newCategoria.setNombre(oldCategoria.getNombre());
        newCategoria.setDescripcion(oldCategoria.getDescripcion());
        categoriaServicePort.updateCategoria(newCategoria);
    }

    @Override
    public void deleteCategoriaInStock(String categoriaNombre){
        categoriaServicePort.deleteCategoria(categoriaNombre);
    }
}
