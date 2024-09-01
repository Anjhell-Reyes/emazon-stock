package bootcamp.emazon.stock.infrastructure.out.adapter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import bootcamp.emazon.stock.domain.model.Brand;
import bootcamp.emazon.stock.infrastructure.exception.BrandAlreadyExistsException;
import bootcamp.emazon.stock.infrastructure.exception.BrandNotFoundException;
import bootcamp.emazon.stock.infrastructure.out.entity.BrandEntity;
import bootcamp.emazon.stock.infrastructure.out.mapper.BrandEntityMapper;
import bootcamp.emazon.stock.infrastructure.out.repository.IBrandRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

        assertThrows(BrandAlreadyExistsException.class, () -> brandJpaAdapter.saveBrand(brand));
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