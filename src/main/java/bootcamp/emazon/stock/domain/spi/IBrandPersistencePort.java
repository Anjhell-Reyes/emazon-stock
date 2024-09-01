package bootcamp.emazon.stock.domain.spi;

import bootcamp.emazon.stock.domain.model.Brand;
import bootcamp.emazon.stock.domain.model.Category;
import bootcamp.emazon.stock.domain.pagination.CategoryPaginated;

import java.util.List;

public interface IBrandPersistencePort {
    Brand saveBrand(Brand brand);

    Brand getBrand(String brandName);

    void updateBrand(Brand brand);
    void deleteBrand(String brandName);
}
