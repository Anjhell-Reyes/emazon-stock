package bootcamp.emazon.stock.infrastructure.out.repository;

import bootcamp.emazon.stock.infrastructure.out.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IBrandRepository extends JpaRepository<BrandEntity, Long> {

    Optional<BrandEntity> findByName(String brandName);

    void deleteByName(String brandName);
}