package bootcamp.emazon.stock.domain.usecase;

import bootcamp.emazon.stock.domain.exception.*;
import bootcamp.emazon.stock.domain.model.Brand;
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
    void testGetAllBrands_ValidPageIndex() {
        // Arrange
        int page = 1;
        int size = 10;
        String sortBy = "name";
        boolean asc = true;
        int offset = (page - 1) * size;

        // Mocked response from the persistence port
        CustomPage<Brand> mockedPage = new CustomPage<>();
        when(brandPersistencePort.getAllBrands(offset, size, sortBy, asc)).thenReturn(mockedPage);

        // Act
        CustomPage<Brand> result = brandUseCase.getAllBrands(page, size, sortBy, asc);

        // Assert
        assertEquals(mockedPage, result);
        verify(brandPersistencePort).getAllBrands(offset, size, sortBy, asc);
    }

    @Test
    void testGetAllBrands_InvalidPageIndex() {
        // Arrange
        int page = -1;
        int size = 10;
        String sortBy = "name";
        boolean asc = true;

        // Act & Assert
        assertThrows(InvalidPageIndexException.class, () -> {
            brandUseCase.getAllBrands(page, size, sortBy, asc);
        });

        // Ensure that the persistence port was not called
        verify(brandPersistencePort, never()).getAllBrands(anyInt(), anyInt(), anyString(), anyBoolean());
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