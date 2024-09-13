package bootcamp.emazon.stock.infrastructure.out.adapter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import bootcamp.emazon.stock.domain.exception.DescriptionMax120CharactersException;
import bootcamp.emazon.stock.domain.model.Brand;
import bootcamp.emazon.stock.application.dto.brandDto.BrandPaginated;
import bootcamp.emazon.stock.domain.exception.BrandNotFoundException;
import bootcamp.emazon.stock.domain.exception.NoDataFoundException;
import bootcamp.emazon.stock.infrastructure.out.entity.BrandEntity;
import bootcamp.emazon.stock.infrastructure.out.mapper.BrandEntityMapper;
import bootcamp.emazon.stock.infrastructure.out.repository.IBrandRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BrandJpaAdapterTest {

    @Mock
    private IBrandRepository brandRepository;

    @Mock
    private BrandEntityMapper brandEntityMapper;

    @InjectMocks
    private BrandJpaAdapter brandJpaAdapter;

    @Test
    void testSaveBrand_BrandAlreadyExists() {
        Brand brand = new Brand();
        brand.setName("Test Brand");
        brand.setDescription("Test Description");
        when(brandRepository.findByName(anyString())).thenReturn(Optional.of(new BrandEntity()));

        assertThrows(DescriptionMax120CharactersException.BrandAlreadyExistsException.class, () -> brandJpaAdapter.saveBrand(brand));
    }

    @Test
    void testSaveBrand_Success() {
        Brand brand = new Brand();
        brand.setName("Test Brand");
        brand.setDescription("Test Description");
        when(brandRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(brandEntityMapper.toEntity(any(Brand.class))).thenReturn(new BrandEntity());

        Brand result = brandJpaAdapter.saveBrand(brand);

        assertEquals(brand, result);
        verify(brandRepository, times(1)).save(any(BrandEntity.class));
    }

    @Test
    void testGetBrand_BrandNotFound() {
        when(brandRepository.findByName(anyString())).thenReturn(Optional.empty());

        assertThrows(BrandNotFoundException.class, () -> brandJpaAdapter.getBrand("Nonexistent Brand"));
    }

    @Test
    void testGetBrand_Success() {
        BrandEntity brandEntity = new BrandEntity();
        Brand brand = new Brand();
        brand.setName("Test Brand");
        brand.setDescription("Test Description");
        when(brandRepository.findByName(anyString())).thenReturn(Optional.of(brandEntity));
        when(brandEntityMapper.toBrand(any(BrandEntity.class))).thenReturn(brand);

        Brand result = brandJpaAdapter.getBrand("Test Brand");

        assertNotNull(result);
        assertEquals("Test Brand", result.getName());
    }

    @Test
    void testGetAllBrandsWithResults() {
        // Arrange
        int offset = 0;
        int limit = 10;
        String sortBy = "name";
        boolean asc = true;

        Sort sort = Sort.by(asc ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(offset / limit, limit, sort);

        BrandEntity brandEntity = new BrandEntity(); // Sample brand entity
        Brand brand = new Brand(); // Sample brand

        when(brandRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(brandEntity)));
        when(brandEntityMapper.toBrand(brandEntity)).thenReturn(brand);

        // Act
        List<Brand> result = brandJpaAdapter.getAllBrands(offset, limit, sortBy, asc);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(brand, result.get(0));
        verify(brandRepository).findAll(pageable);
        verify(brandEntityMapper).toBrand(brandEntity);
    }

    @Test
    void testGetAllBrandsNoResults() {
        // Arrange
        int offset = 0;
        int limit = 10;
        String sortBy = "name";
        boolean asc = true;

        Sort sort = Sort.by(asc ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(offset / limit, limit, sort);

        when(brandRepository.findAll(pageable)).thenReturn(Page.empty());

        // Act & Assert
        assertThrows(NoDataFoundException.class, () -> brandJpaAdapter.getAllBrands(offset, limit, sortBy, asc));
        verify(brandRepository).findAll(pageable);
    }


    @Test
    void testUpdateBrand() {
        Brand brand = new Brand();
        brand.setName("Test Brand");
        brand.setDescription("Test Description");
        when(brandEntityMapper.toEntity(any(Brand.class))).thenReturn(new BrandEntity());

        assertDoesNotThrow(() -> brandJpaAdapter.updateBrand(brand));
        verify(brandRepository, times(1)).save(any(BrandEntity.class));
    }

    @Test
    void testDeleteBrand() {
        doNothing().when(brandRepository).deleteByName(anyString());

        assertDoesNotThrow(() -> brandJpaAdapter.deleteBrand("Test Brand"));
        verify(brandRepository, times(1)).deleteByName(anyString());
    }
}