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
import bootcamp.emazon.stock.domain.model.CustomPage;
import bootcamp.emazon.stock.infrastructure.out.entity.BrandEntity;
import bootcamp.emazon.stock.infrastructure.out.mapper.BrandEntityMapper;
import bootcamp.emazon.stock.infrastructure.out.repository.IBrandRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Arrays;
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
    public void testGetAllBrands_Success() {
        // Datos de prueba
        BrandEntity brandEntity1 = new BrandEntity();
        brandEntity1.setId(1L);
        brandEntity1.setName("Brand 1");
        brandEntity1.setDescription("Description 1");

        BrandEntity brandEntity2 = new BrandEntity();
        brandEntity2.setId(2L);
        brandEntity2.setName("Brand 2");
        brandEntity2.setDescription("Description 2");

        List<BrandEntity> brandEntities = Arrays.asList(brandEntity1, brandEntity2);

        Page<BrandEntity> brandPage = new PageImpl<>(brandEntities, PageRequest.of(0, 10), 2);

        Brand brand1 = new Brand(1L, "Brand 1", "Description 1");
        Brand brand2 = new Brand(2L, "Brand 2", "Description 2");
        List<Brand> brands = Arrays.asList(brand1, brand2);

        when(brandRepository.findAll(any(Pageable.class))).thenReturn(brandPage);
        when(brandEntityMapper.toBrand(brandEntity1)).thenReturn(brand1);
        when(brandEntityMapper.toBrand(brandEntity2)).thenReturn(brand2);

        CustomPage<Brand> result = brandJpaAdapter.getAllBrands(0, 10, "name", true);

        assertNotNull(result);
        assertEquals(0, result.getPageNumber());
        assertEquals(10, result.getPageSize());
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(brands, result.getContent());
    }

    @Test
    public void testGetAllBrands_NoDataFound() {
        Page<BrandEntity> brandPage = new PageImpl<>(List.of(), PageRequest.of(0, 10), 0);

        when(brandRepository.findAll(any(Pageable.class))).thenReturn(brandPage);

        assertThrows(NoDataFoundException.class, () -> brandJpaAdapter.getAllBrands(0, 10, "name", true));
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