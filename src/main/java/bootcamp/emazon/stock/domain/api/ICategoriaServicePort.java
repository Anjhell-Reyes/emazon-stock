package bootcamp.emazon.stock.domain.api;

import bootcamp.emazon.stock.domain.model.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface
ICategoriaServicePort {

    Categoria saveCategoria(Categoria categoria);

    Categoria getCategoria(String categoriaNombre);


    void updateCategoria(Categoria categoria);
    void deleteCategoria(String categoriaNombre);

}
