package bootcamp.emazon.stock.application.handler.brandHandler;


import bootcamp.emazon.stock.application.dto.brandDto.BrandRequest;
import bootcamp.emazon.stock.application.dto.brandDto.BrandResponse;
import bootcamp.emazon.stock.application.mapper.BrandMapper;
import bootcamp.emazon.stock.domain.api.IBrandServicePort;
import bootcamp.emazon.stock.domain.model.Brand;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class BrandHandler implements IBrandHandler{
    private final IBrandServicePort brandServicePort;
    private final BrandMapper brandMapper;

    @Override
    public void saveBrandInStock(BrandRequest brandRequest){
        Brand brand = brandMapper.toBrand(brandRequest);
        brandServicePort.saveBrand(brand);
    }

    @Override
    public BrandResponse getBrandFromStock(String brandName){
        Brand brand = brandServicePort.getBrand(brandName);
        return brandMapper.toResponse(brand);
    }

    @Override
    public void updateBrandInStock(BrandRequest brandRequest){
        Brand oldBrand = brandServicePort.getBrand(brandRequest.getName());
        Brand newBrand = brandMapper.toBrand(brandRequest);
        newBrand.setId(oldBrand.getId());
        newBrand.setName(oldBrand.getName());
        newBrand.setDescription(oldBrand.getDescription());
        brandServicePort.updateBrand(newBrand);
    }

    @Override
    public void deleteBrandInStock(String brandNombre){
        brandServicePort.deleteBrand(brandNombre);
    }
}
