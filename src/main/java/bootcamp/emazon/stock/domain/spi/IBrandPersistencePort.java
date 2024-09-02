package bootcamp.emazon.stock.domain.spi;

import bootcamp.emazon.stock.domain.model.Brand;
import bootcamp.emazon.stock.domain.model.Category;
import bootcamp.emazon.stock.domain.pagination.BrandPaginated;
import bootcamp.emazon.stock.domain.pagination.CategoryPaginated;

import java.util.List;

public interface IBrandPersistencePort {
    Brand saveBrand(Brand brand);

    Brand getBrand(String brandName);

    List<BrandPaginated> getAllBrands(int offset, int limit, String sortBy, boolean asc);

    void updateBrand(Brand brand);
    void deleteBrand(String brandName);
}
