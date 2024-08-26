package bootcamp.emazon.stock.domain.usecase;

import bootcamp.emazon.stock.domain.api.ICategoriaServicePort;
import bootcamp.emazon.stock.domain.model.Categoria;
import bootcamp.emazon.stock.domain.spi.ICategoriaPersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class CategoriaUseCase implements ICategoriaServicePort {

    private final ICategoriaPersistencePort categoriaPersistencePort;

    public CategoriaUseCase(ICategoriaPersistencePort categoriaPersistencePort){
        this.categoriaPersistencePort = categoriaPersistencePort;
    }

    @Override
    public Categoria saveCategoria(Categoria categoria){
        return categoriaPersistencePort.saveCategoria(categoria);
    }

    @Override
    public Categoria getCategoria(String categoriaNombre){
        return categoriaPersistencePort.getCategoria(categoriaNombre);
    }

    @Override
    public Page<Categoria> getAllCategorias(Pageable pageable) {
        return categoriaPersistencePort.getAllCategorias(pageable);
    }


    @Override
    public void updateCategoria(Categoria categoria) {
        categoriaPersistencePort.updateCategoria(categoria);
    }

    @Override
    public void deleteCategoria(String categoriaNombre) {
        categoriaPersistencePort.deleteCategoria(categoriaNombre);
    }

}
