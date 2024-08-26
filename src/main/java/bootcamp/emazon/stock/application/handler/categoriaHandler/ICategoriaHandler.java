package bootcamp.emazon.stock.application.handler.categoriaHandler;

import bootcamp.emazon.stock.application.dto.categoriaDto.CategoriaRequest;
import bootcamp.emazon.stock.application.dto.categoriaDto.CategoriaResponse;
import org.springframework.data.domain.Page;

public interface ICategoriaHandler{

    void saveCategoriaInStock(CategoriaRequest categoriaRequest);

    CategoriaResponse getCategoriaFromStock(String categoriaNombre);

    void updateCategoriaInStock(CategoriaRequest categoriaRequest);
    void deleteCategoriaInStock(String categoriaNombre);


}
