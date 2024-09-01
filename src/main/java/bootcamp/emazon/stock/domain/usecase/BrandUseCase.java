package bootcamp.emazon.stock.domain.usecase;

import bootcamp.emazon.stock.domain.api.IBrandServicePort;
import bootcamp.emazon.stock.domain.exception.*;
import bootcamp.emazon.stock.domain.model.Brand;
import bootcamp.emazon.stock.domain.spi.IBrandPersistencePort;

public class BrandUseCase implements IBrandServicePort {

    private final IBrandPersistencePort brandPersistencePort;

    public BrandUseCase(IBrandPersistencePort brandPersistencePort){
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public Brand saveBrand(Brand brand){

        int maxLenghtName = 50;
        int maxLenghtDescription = 120;

        if (brand.getName() == null) {
            throw new NamenotnullException();
        }
        if(brand.getName().length() > maxLenghtName){
            throw new NameMax50CharactersException();
        }
        if (brand.getDescription() == null) {
            throw new DescriptionNotnullException();
        }
        if(brand.getDescription().isEmpty()){
            throw new DescriptionEmptyException();
        }
        if(brand.getDescription().length() > maxLenghtDescription) {
            throw new DescriptionMax120CharactersException();
        }
        return brandPersistencePort.saveBrand(brand);
    }

    @Override
    public Brand getBrand(String brandName){
        return brandPersistencePort.getBrand(brandName);
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
