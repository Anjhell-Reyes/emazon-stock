package bootcamp.emazon.stock.application.handler.brandHandler;

import bootcamp.emazon.stock.application.dto.brandDto.BrandRequest;
import bootcamp.emazon.stock.application.dto.brandDto.BrandResponse;

public interface IBrandHandler {

    void saveBrandInStock(BrandRequest brandRequest);

    BrandResponse getBrandFromStock(String brandName);

    void updateBrandInStock(BrandRequest brandRequest);

    void deleteBrandInStock(String brandName);
}
