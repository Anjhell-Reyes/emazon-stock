package bootcamp.emazon.stock.domain.api;

import bootcamp.emazon.stock.domain.model.Brand;
import bootcamp.emazon.stock.domain.pagination.CategoryPaginated;

import java.util.List;

public interface IBrandServicePort {

    Brand saveBrand(Brand brand);

    Brand getBrand(String brandName);

    void updateBrand(Brand brand);
    void deleteBrand(String brandName);
}