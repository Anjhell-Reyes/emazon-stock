package bootcamp.emazon.stock.infrastructure.out.repository;
import bootcamp.emazon.stock.infrastructure.out.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICategoryRepository extends JpaRepository<CategoryEntity, Long> {

    Optional<CategoryEntity> findByName(String categoryName);

    void deleteByName(String categoryName);
}

