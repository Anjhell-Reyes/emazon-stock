package bootcamp.emazon.stock.domain.api;

import bootcamp.emazon.stock.domain.model.Brand;
import bootcamp.emazon.stock.domain.pagination.BrandPaginated;
import bootcamp.emazon.stock.domain.pagination.CategoryPaginated;

import java.util.List;

public interface IBrandServicePort {

    Brand saveBrand(Brand brand);

    Brand getBrand(String brandName);

    List<BrandPaginated> getAllBrands(int page, int size, String sortBy, boolean asc);

    void updateBrand(Brand brand);
    void deleteBrand(String brandName);
}