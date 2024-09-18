package bootcamp.emazon.stock.domain.spi;

import bootcamp.emazon.stock.domain.model.Brand;
import bootcamp.emazon.stock.domain.model.CustomPage;

import java.util.List;

public interface IBrandPersistencePort {
    Brand saveBrand(Brand brand);

    Brand getBrand(String brandName);

    CustomPage<Brand> getAllBrands(int offset, int limit, String sortBy, boolean asc);

    List<Brand> getAll();

    void updateBrand(Brand brand);
    void deleteBrand(String brandName);
}
