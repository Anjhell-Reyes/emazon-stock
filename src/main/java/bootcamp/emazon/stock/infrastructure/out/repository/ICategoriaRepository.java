package bootcamp.emazon.stock.infrastructure.out.repository;
import bootcamp.emazon.stock.infrastructure.out.entity.CategoriaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICategoriaRepository extends JpaRepository<CategoriaEntity, Long> {

    Optional<CategoriaEntity> findByNombre(String categoriaNombre);

    Optional<CategoriaEntity> deleteByNombre(String categoriaNombre);

}

