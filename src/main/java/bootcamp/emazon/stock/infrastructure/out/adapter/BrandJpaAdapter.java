package bootcamp.emazon.stock.infrastructure.out.adapter;

import bootcamp.emazon.stock.domain.model.Brand;
import bootcamp.emazon.stock.domain.spi.IBrandPersistencePort;
import bootcamp.emazon.stock.infrastructure.exception.BrandAlreadyExistsException;
import bootcamp.emazon.stock.infrastructure.exception.BrandNotFoundException;
import bootcamp.emazon.stock.infrastructure.out.mapper.BrandEntityMapper;
import bootcamp.emazon.stock.infrastructure.out.repository.IBrandRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BrandJpaAdapter implements IBrandPersistencePort {
    private final IBrandRepository brandRepository;

    private final BrandEntityMapper brandEntityMapper;

    public IBrandRepository getBrandRepository() {
        return brandRepository;
    }

    public BrandEntityMapper getBrandEntityMapper() {
        return brandEntityMapper;
    }

    @Override
    public Brand saveBrand(Brand brand){
        if(brandRepository.findByName(brand.getName()).isPresent()){
            throw new BrandAlreadyExistsException();
        }
        brandRepository.save(brandEntityMapper.toEntity(brand));
        return brand;
    }
    @Override
    public Brand getBrand(String brandName){
        return brandEntityMapper.toBrand(brandRepository.findByName(brandName).orElseThrow(BrandNotFoundException::new));
    }

    @Override
    public void updateBrand(Brand brand) { brandRepository.save(brandEntityMapper.toEntity(brand));}

    @Override
    public void deleteBrand(String brandName){ brandRepository.deleteByName(brandName);}
}
