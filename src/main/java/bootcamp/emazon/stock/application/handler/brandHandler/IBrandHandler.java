package bootcamp.emazon.stock.application.handler.brandHandler;

import bootcamp.emazon.stock.application.dto.brandDto.BrandRequest;
import bootcamp.emazon.stock.application.dto.brandDto.BrandResponse;
import bootcamp.emazon.stock.domain.pagination.BrandPaginated;
import bootcamp.emazon.stock.domain.pagination.CategoryPaginated;

import java.util.List;

public interface IBrandHandler {

    void saveBrandInStock(BrandRequest brandRequest);

    BrandResponse getBrandFromStock(String brandName);

    List<BrandPaginated> getAllBrandsFromStock(int page, int size, String sortBy, boolean asc);

    void updateBrandInStock(BrandRequest brandRequest);

    void deleteBrandInStock(String brandName);
}
