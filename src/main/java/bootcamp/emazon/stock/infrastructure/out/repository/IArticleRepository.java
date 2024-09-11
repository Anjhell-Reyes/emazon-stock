package bootcamp.emazon.stock.infrastructure.out.repository;

import bootcamp.emazon.stock.infrastructure.out.entity.ArticleEntity;
import bootcamp.emazon.stock.infrastructure.out.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IArticleRepository extends JpaRepository<ArticleEntity, Long> {

    Optional<ArticleEntity> findByName(String articleName);

    Optional<ArticleEntity> findByBrand(BrandEntity articleBrand);

    void deleteByName(String categoryName);
}