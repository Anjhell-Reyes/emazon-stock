package bootcamp.emazon.stock.domain.usecase;

import bootcamp.emazon.stock.domain.api.IBrandServicePort;
import bootcamp.emazon.stock.domain.exception.*;
import bootcamp.emazon.stock.domain.model.Brand;
import bootcamp.emazon.stock.application.dto.brandDto.BrandPaginated;
import bootcamp.emazon.stock.domain.model.CustomPage;
import bootcamp.emazon.stock.domain.spi.IBrandPersistencePort;
import bootcamp.emazon.stock.domain.utils.Constants;

import java.util.List;

public class BrandUseCase implements IBrandServicePort {

    private final IBrandPersistencePort brandPersistencePort;

    public BrandUseCase(IBrandPersistencePort brandPersistencePort){
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public Brand saveBrand(Brand brand){

        if (brand.getName() == null) {
            throw new NamenotnullException();
        }
        if(brand.getName().length() > Constants.MAX_LENGHT_NAME){
            throw new NameMax50CharactersException();
        }
        if (brand.getDescription() == null) {
            throw new DescriptionNotnullException();
        }
        if(brand.getDescription().isEmpty()){
            throw new DescriptionEmptyException();
        }
        if(brand.getDescription().length() > Constants.MAX_LENGHT_DESCRIPTION_BRAND) {
            throw new DescriptionMax120CharactersException();
        }
        return brandPersistencePort.saveBrand(brand);
    }

    @Override
    public Brand getBrand(String brandName){
        return brandPersistencePort.getBrand(brandName);
    }

    @Override
    public CustomPage<Brand> getAllBrands(int page, int size, String sortBy, boolean asc) {
        if (page < 0) {
            throw new InvalidPageIndexException();
        }
        int offset = (page - 1) * size;

        return brandPersistencePort.getAllBrands(offset, size, sortBy, asc);
    }


    @Override
    public List<Brand> getAll(){
        return brandPersistencePort.getAll();
    }

    @Override
    public void updateBrand(Brand brand) {
        if (brand.getName() == null) {
            throw new NamenotnullException();
        }
        if (brand.getDescription() == null) {
            throw new DescriptionNotnullException();
        }
        brandPersistencePort.updateBrand(brand);
    }

    @Override
    public void deleteBrand(String brandName) {
        brandPersistencePort.deleteBrand(brandName);
    }

}
