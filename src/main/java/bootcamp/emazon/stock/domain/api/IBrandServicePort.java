package bootcamp.emazon.stock.domain.api;

import bootcamp.emazon.stock.domain.model.Brand;
import bootcamp.emazon.stock.domain.model.CustomPage;

import java.util.List;

public interface IBrandServicePort {

    Brand saveBrand(Brand brand);

    Brand getBrand(String brandName);

    CustomPage<Brand> getAllBrands(int page, int size, String sortBy, boolean asc);

    List<Brand> getAll();

    void updateBrand(Brand brand);
    void deleteBrand(String brandName);
}