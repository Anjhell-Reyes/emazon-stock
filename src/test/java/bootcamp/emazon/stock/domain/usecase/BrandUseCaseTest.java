package bootcamp.emazon.stock.domain.usecase;

import bootcamp.emazon.stock.domain.exception.DescriptionNotnullException;
import bootcamp.emazon.stock.domain.exception.InvalidPageIndexException;
import bootcamp.emazon.stock.domain.exception.NamenotnullException;
import bootcamp.emazon.stock.domain.model.Brand;
import bootcamp.emazon.stock.domain.pagination.BrandPaginated;
import bootcamp.emazon.stock.domain.spi.IBrandPersistencePort;
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

    @Test
    void testSaveBrand_Success() {
        // Arrange
        Brand brand = new Brand(1L, "ValidName", "ValidDescription");
        when(brandPersistencePort.saveBrand(any(Brand.class))).thenReturn(brand);

        // Act
        Brand result = brandUseCase.saveBrand(brand);

        // Assert
        assertEquals(brand, result);
        verify(brandPersistencePort, times(1)).saveBrand(brand);
    }

    @Test
    void testSaveBrand_InvalidName() {
        // Arrange
        Brand brand = new Brand(1L, "", "ValidDescription");

        // Act & Assert
        assertThrows(NamenotnullException.class, () -> brandUseCase.saveBrand(brand));
    }

    @Test
    void testSaveBrand_InvalidDescription() {
        // Arrange
        Brand brand = new Brand(1L, "ValidName", "");

        // Act & Assert
        assertThrows(DescriptionNotnullException.class, () -> brandUseCase.saveBrand(brand));
    }

    @Test
    void testGetBrand_Success() {
        // Arrange
        Brand brand = new Brand(1L, "ValidName", "ValidDescription");
        when(brandPersistencePort.getBrand("ValidName")).thenReturn(brand);

        // Act
        Brand result = brandUseCase.getBrand("ValidName");

        // Assert
        assertEquals(brand, result);
        verify(brandPersistencePort, times(1)).getBrand("ValidName");
    }

    @Test
    void testGetAllBrands_Success() {
        // Arrange
        BrandPaginated paginatedBrand = new BrandPaginated(1L, "ValidName", "ValidDescription");
        List<BrandPaginated> brands = Collections.singletonList(paginatedBrand);
        when(brandPersistencePort.getAllBrands(anyInt(), anyInt(), anyString(), anyBoolean())).thenReturn(brands);

        // Act
        List<BrandPaginated> result = brandUseCase.getAllBrands(1, 10, "name", true);

        // Assert
        assertEquals(brands, result);
        verify(brandPersistencePort, times(1)).getAllBrands(0, 10, "name", true);
    }

    @Test
    void testGetAllBrands_InvalidPage() {
        // Act & Assert
        assertThrows(InvalidPageIndexException.class, () -> brandUseCase.getAllBrands(-1, 10, "name", true));
    }

    @Test
    void testUpdateBrand_Success() {
        // Arrange
        Brand brand = new Brand(1L, "ValidName", "ValidDescription");
        doNothing().when(brandPersistencePort).updateBrand(any(Brand.class));

        // Act
        assertDoesNotThrow(() -> brandUseCase.updateBrand(brand));

        // Assert
        verify(brandPersistencePort, times(1)).updateBrand(brand);
    }

    @Test
    void testUpdateBrand_InvalidName() {
        // Arrange
        Brand brand = new Brand(1L, "", "ValidDescription");

        // Act & Assert
        assertThrows(NamenotnullException.class, () -> brandUseCase.updateBrand(brand));
    }

    @Test
    void testDeleteBrand_Success() {
        // Arrange
        doNothing().when(brandPersistencePort).deleteBrand("ValidName");

        // Act
        assertDoesNotThrow(() -> brandUseCase.deleteBrand("ValidName"));

        // Assert
        verify(brandPersistencePort, times(1)).deleteBrand("ValidName");
    }

}