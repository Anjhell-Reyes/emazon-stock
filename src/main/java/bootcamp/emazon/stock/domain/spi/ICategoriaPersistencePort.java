package bootcamp.emazon.stock.domain.spi;

import bootcamp.emazon.stock.domain.model.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICategoriaPersistencePort {

    Categoria saveCategoria(Categoria categoria);

    Categoria getCategoria(String categoriaNombre);

    Page<Categoria> getAllCategorias(Pageable pageable);

    void updateCategoria(Categoria categoria);
    void deleteCategoria(String categoriaNombre);
}
