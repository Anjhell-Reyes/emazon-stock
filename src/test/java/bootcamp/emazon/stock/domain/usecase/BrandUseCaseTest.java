package bootcamp.emazon.stock.domain.usecase;

import bootcamp.emazon.stock.domain.exception.*;
import bootcamp.emazon.stock.domain.model.Brand;
import bootcamp.emazon.stock.application.dto.brandDto.BrandPaginated;
import bootcamp.emazon.stock.domain.model.CustomPage;
import bootcamp.emazon.stock.domain.spi.IBrandPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BrandUseCaseTest {
    @Mock
    private IBrandPersistencePort brandPersistencePort;

    @InjectMocks
    private BrandUseCase brandUseCase;

    private Brand brand;

    @BeforeEach
    void setUp() {
        brand = new Brand(1L, "Samsung", "Technology and gadgets");
    }

    @Test
    void saveBrand_Success() {
        when(brandPersistencePort.saveBrand(any(Brand.class))).thenReturn(brand);

        Brand savedBrand = brandUseCase.saveBrand(brand);

        assertNotNull(savedBrand);
        assertEquals(brand.getName(), savedBrand.getName());
        verify(brandPersistencePort, times(1)).saveBrand(any(Brand.class));
    }

    @Test
    void saveBrand_ShouldThrowNamenotnullException_WhenNameIsNull() {
        brand.setName(null);

        assertThrows(NamenotnullException.class, () -> brandUseCase.saveBrand(brand));
    }

    @Test
    void saveBrand_ShouldThrowNameMax50CharactersException_WhenNameExceedsMaxLength() {
        brand.setName("A".repeat(51)); // nombre de 51 caracteres

        assertThrows(NameMax50CharactersException.class, () -> brandUseCase.saveBrand(brand));
    }

    @Test
    void saveBrand_ShouldThrowDescriptionNotnullException_WhenDescriptionIsNull() {
        brand.setDescription(null);

        assertThrows(DescriptionNotnullException.class, () -> brandUseCase.saveBrand(brand));
    }

    @Test
    void saveBrand_ShouldThrowDescriptionEmptyException_WhenDescriptionIsEmpty() {
        brand.setDescription("");

        assertThrows(DescriptionEmptyException.class, () -> brandUseCase.saveBrand(brand));
    }

    @Test
    void saveBrand_ShouldThrowDescriptionMax120CharactersException_WhenDescriptionExceedsMaxLength() {
        brand.setDescription("A".repeat(121)); // descripción de 121 caracteres

        assertThrows(DescriptionMax120CharactersException.class, () -> brandUseCase.saveBrand(brand));
    }

    @Test
    void getBrand_Success() {
        when(brandPersistencePort.getBrand(anyString())).thenReturn(brand);

        Brand foundBrand = brandUseCase.getBrand("Samsung");

        assertNotNull(foundBrand);
        assertEquals("Samsung", foundBrand.getName());
        verify(brandPersistencePort, times(1)).getBrand(anyString());
    }

    @Test
    void getAllBrands_ShouldThrowInvalidPageIndexException_WhenPageIsNegative() {
        assertThrows(InvalidPageIndexException.class, () -> brandUseCase.getAllBrands(-1, 10, "name", true));
    }

    @Test
    void getAll_Success() {
        List<Brand> brands = Collections.singletonList(brand);
        when(brandPersistencePort.getAll()).thenReturn(brands);

        List<Brand> allBrands = brandUseCase.getAll();

        assertNotNull(allBrands);
        assertEquals(1, allBrands.size());
        verify(brandPersistencePort, times(1)).getAll();
    }

    @Test
    void testGetAllBrandsSuccessfully() {
        // Arrange
        int page = 1;
        int size = 10;
        String sortBy = "name";
        boolean asc = true;
        int offset = (page - 1) * size;

        List<Brand> brands = Collections.singletonList(new Brand(1L, "Sony", "Electronics Brand"));
        long totalElements = 1L;

        // Mocking
        when(brandPersistencePort.getAllBrands(offset, size, sortBy, asc)).thenReturn(brands);
        when(brandPersistencePort.countBrands()).thenReturn(totalElements);

        // Act
        CustomPage<Brand> result = brandUseCase.getAllBrands(page, size, sortBy, asc);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(page, result.getPageNumber());
        assertEquals(size, result.getPageSize());
        assertEquals(totalElements, result.getTotalElements());

        verify(brandPersistencePort, times(1)).getAllBrands(offset, size, sortBy, asc);
        verify(brandPersistencePort, times(1)).countBrands();
    }

    @Test
    void testGetAllBrandsThrowsInvalidPageIndexException() {
        // Arrange
        int page = -1; // Valor inválido
        int size = 10;
        String sortBy = "name";
        boolean asc = true;

        // Act & Assert
        assertThrows(InvalidPageIndexException.class, () -> brandUseCase.getAllBrands(page, size, sortBy, asc));

        // No se debe llamar a estos métodos
        verify(brandPersistencePort, never()).getAllBrands(anyInt(), anyInt(), anyString(), anyBoolean());
        verify(brandPersistencePort, never()).countBrands();
    }

    @Test
    void updateBrand_Success() {
        doNothing().when(brandPersistencePort).updateBrand(any(Brand.class));

        brandUseCase.updateBrand(brand);

        verify(brandPersistencePort, times(1)).updateBrand(any(Brand.class));
    }

    @Test
    void updateBrand_ShouldThrowNamenotnullException_WhenNameIsNull() {
        brand.setName(null);

        assertThrows(NamenotnullException.class, () -> brandUseCase.updateBrand(brand));
    }

    @Test
    void updateBrand_ShouldThrowDescriptionNotnullException_WhenDescriptionIsNull() {
        brand.setDescription(null);

        assertThrows(DescriptionNotnullException.class, () -> brandUseCase.updateBrand(brand));
    }

    @Test
    void deleteBrand_Success() {
        doNothing().when(brandPersistencePort).deleteBrand(anyString());

        brandUseCase.deleteBrand("Samsung");

        verify(brandPersistencePort, times(1)).deleteBrand(anyString());
    }
}