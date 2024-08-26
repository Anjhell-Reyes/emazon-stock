package bootcamp.emazon.stock.infrastructure.out.adapter;

import bootcamp.emazon.stock.domain.model.Categoria;
import bootcamp.emazon.stock.domain.spi.ICategoriaPersistencePort;
import bootcamp.emazon.stock.infrastructure.exception.*;
import bootcamp.emazon.stock.infrastructure.out.entity.CategoriaEntity;
import bootcamp.emazon.stock.infrastructure.out.mapper.CategoriaEntityMapper;
import bootcamp.emazon.stock.infrastructure.out.repository.ICategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class CategoriaJpaAdapter implements ICategoriaPersistencePort {

    private final ICategoriaRepository categoriaRepository;

    private final CategoriaEntityMapper categoriaEntityMapper;

    public ICategoriaRepository getCategoriaRepository() {
        return categoriaRepository;
    }

    public CategoriaEntityMapper getCategoriaEntityMapper() {
        return categoriaEntityMapper;
    }

    @Override
    public Categoria saveCategoria(Categoria categoria){
        if(categoriaRepository.findByNombre(categoria.getNombre()).isPresent()){
            throw new CategoriaAlreadyExistsExeption();
        }
        categoriaRepository.save(categoriaEntityMapper.toEntity(categoria));
        return categoria;
    }
    @Override
    public Categoria getCategoria(String categoriaNombre){
        return categoriaEntityMapper.toCategoria(categoriaRepository.findByNombre(categoriaNombre).orElseThrow(CategoriaNotFoundException::new));
    }



    @Override
    public void updateCategoria(Categoria categoria) { categoriaRepository.save(categoriaEntityMapper.toEntity(categoria));}

    @Override
    public void deleteCategoria(String categoriaNombre){ categoriaRepository.deleteByNombre(categoriaNombre);}
}

