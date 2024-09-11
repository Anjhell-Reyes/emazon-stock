package bootcamp.emazon.stock.infrastructure.out.adapter;

import bootcamp.emazon.stock.domain.exception.DescriptionMax120CharactersException;
import bootcamp.emazon.stock.domain.model.Brand;
import bootcamp.emazon.stock.domain.pagination.BrandPaginated;
import bootcamp.emazon.stock.domain.spi.IBrandPersistencePort;
import bootcamp.emazon.stock.domain.exception.BrandNotFoundException;
import bootcamp.emazon.stock.domain.exception.NoDataFoundException;
import bootcamp.emazon.stock.infrastructure.out.entity.BrandEntity;
import bootcamp.emazon.stock.infrastructure.out.mapper.BrandEntityMapper;
import bootcamp.emazon.stock.infrastructure.out.repository.IBrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

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
            throw new DescriptionMax120CharactersException.BrandAlreadyExistsException();
        }
        brandRepository.save(brandEntityMapper.toEntity(brand));
        return brand;
    }
    @Override
    public Brand getBrand(String brandName){
        return brandEntityMapper.toBrand(brandRepository.findByName(brandName).orElseThrow(BrandNotFoundException::new));
    }

    @Override
    public List<BrandPaginated> getAllBrands(int offset, int limit, String sortBy, boolean asc) {
        Sort sort = Sort.by(asc ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(offset / limit, limit, sort);
        Page<BrandEntity> page = brandRepository.findAll(pageable);

        if (page.isEmpty()) {
            throw new NoDataFoundException();
        }

        return page.stream()
                .map(this::toBrandPaginated)
                .collect(Collectors.toList());
    }

    @Override
    public List<Brand> getAll(){
        List<BrandEntity> brandEntities = brandRepository.findAll();
        return brandEntities.stream()
                .map(brandEntityMapper::toBrand)
                .collect(Collectors.toList());
    }

    private BrandPaginated toBrandPaginated(BrandEntity brandEntity) {
        return new BrandPaginated(brandEntity.getId(), brandEntity.getName(), brandEntity.getDescription());
    }


    @Override
    public void updateBrand(Brand brand) { brandRepository.save(brandEntityMapper.toEntity(brand));}

    @Override
    public void deleteBrand(String brandName){ brandRepository.deleteByName(brandName);}
}
