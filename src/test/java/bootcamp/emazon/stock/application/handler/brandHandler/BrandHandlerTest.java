package bootcamp.emazon.stock.application.handler.brandHandler;

import bootcamp.emazon.stock.application.dto.brandDto.BrandRequest;
import bootcamp.emazon.stock.application.dto.brandDto.BrandResponse;
import bootcamp.emazon.stock.application.mapper.BrandMapper;
import bootcamp.emazon.stock.domain.api.IBrandServicePort;
import bootcamp.emazon.stock.domain.model.Brand;
import bootcamp.emazon.stock.application.dto.brandDto.BrandPaginated;
import bootcamp.emazon.stock.domain.model.CustomPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class BrandHandlerTest {
    // Aquí se definen las interfaces y clases utilizadas por la clase a probar
    @Mock
    private IBrandServicePort brandServicePort;

    @Mock
    private BrandMapper brandMapper;

    // Se define la clase a probar y se inyectan los mocks
    @InjectMocks
    private BrandHandler brandHandler;

    // Especifica que es un test
    @Test
// Para darle más contexto al test
    @DisplayName("Al guardar una marca debe llamar el mapper y el servicio")
    void saveBrandInStock() {

        BrandRequest brandRequest = new BrandRequest();
        brandRequest.setName("TestNombre");
        brandRequest.setDescription("TestDescripcion");

        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("TestNombre");
        brand.setDescription("TestDescripcion");

        // Cuando el mapper de modelo reciba algún request, este debe retornar un modelo
        when(brandMapper.toBrand(any(BrandRequest.class))).thenReturn(brand);

        // Invocar el método guardar
        brandHandler.saveBrandInStock(brandRequest);

        // Verificar cuántas veces se invocó el método
        verify(brandMapper, times(1)).toBrand(brandRequest);
        verify(brandServicePort, times(1)).saveBrand(brand);
    }

    @Test
    void saveBrandInStock_ShouldCallMapperAndService() {
        BrandRequest request = new BrandRequest();
        request.setName("Electrónica");
        request.setDescription("Productos electrónicos");

        Brand brand = new Brand(1L, "testName", "descriptionTest");
        when(brandMapper.toBrand(any(BrandRequest.class))).thenReturn(brand);

        brandHandler.saveBrandInStock(request);

        verify(brandMapper, times(1)).toBrand(request);
        verify(brandServicePort, times(1)).saveBrand(brand);
    }

    @Test
    void testGetBrandFromStock() {
        // Datos simulados
        String brandName = "Electrónica";

        Brand brand = new Brand(1L, "Electrónica", "Categoría de electrónicos");
        BrandResponse expectedResponse = new BrandResponse();
        expectedResponse.setId(1L);
        expectedResponse.setName("Electrónica");
        expectedResponse.setDescription("Categoría de electrónicos");

        // Simulación del comportamiento del mock
        when(brandServicePort.getBrand(brandName)).thenReturn(brand);
        when(brandMapper.toResponse(brand)).thenReturn(expectedResponse);

        // Llamada al método a probar
        BrandResponse actuallyResponse = brandHandler.getBrandFromStock(brandName);

        // Verificación de resultados
        assertEquals(expectedResponse, actuallyResponse);
    }

    @Test
    void testGetAllBrandsFromStockSuccessfully() {
        // Arrange
        int page = 1;
        int size = 1;
        String sortBy = "name";
        boolean asc = true;

        Brand brand = new Brand(1L, "Sony", "Electronics Brand");
        BrandPaginated brandPaginated = new BrandPaginated();
        brandPaginated.setName("Sony");
        brandPaginated.setDescription("Electronics Brand");
        List<Brand> brands = Collections.singletonList(brand);

        CustomPage<Brand> customPage = new CustomPage<>(brands, page, size, brands.size());

        when(brandServicePort.getAllBrands(page, size, sortBy, asc)).thenReturn(customPage);
        when(brandMapper.toBrandPaginated(brand)).thenReturn(brandPaginated);

        // Act
        Page<BrandPaginated> result = brandHandler.getAllBrandsFromStock(page, size, sortBy, asc);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getContent().size());
        assertEquals("Sony", result.getContent().get(0).getName());

        verify(brandServicePort, times(1)).getAllBrands(page, size, sortBy, asc);
        verify(brandMapper, times(1)).toBrandPaginated(brand);
    }

    @Test
    void testGetAllBrandsFromStockEmptyResult() {
        // Arrange
        int page = 1;
        int size = 10;
        String sortBy = "name";
        boolean asc = true;

        List<Brand> brands = Collections.emptyList();
        CustomPage<Brand> customPage = new CustomPage<>(brands, page, size, 0);

        when(brandServicePort.getAllBrands(page, size, sortBy, asc)).thenReturn(customPage);

        // Act
        Page<BrandPaginated> result = brandHandler.getAllBrandsFromStock(page, size, sortBy, asc);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        assertEquals(0, result.getContent().size());

        verify(brandServicePort, times(1)).getAllBrands(page, size, sortBy, asc);
        verify(brandMapper, never()).toBrandPaginated(any(Brand.class));
    }

    @Test
    void updateBrandInStock_ShouldUpdateBrand() {
        BrandRequest brandRequest = new BrandRequest();
        brandRequest.setName("Electrónica");
        brandRequest.setDescription("Productos electrónicos");

        Brand oldBrand = new Brand(1L, "Electrónica", "Descripción antigua");
        Brand newBrand = new Brand(1L, "Electrónica", "Productos electrónicos");

        when(brandServicePort.getBrand(brandRequest.getName())).thenReturn(oldBrand);
        when(brandMapper.toBrand(brandRequest)).thenReturn(newBrand);

        brandHandler.updateBrandInStock(brandRequest);

        verify(brandServicePort, times(1)).getBrand(brandRequest.getName());
        verify(brandMapper, times(1)).toBrand(brandRequest);
        verify(brandServicePort, times(1)).updateBrand(newBrand);
    }

    @Test
    void deleteBrandInStock_ShouldCallDelete() {
        String brandName = "Electrónica";

        brandHandler.deleteBrandInStock(brandName);

        verify(brandServicePort, times(1)).deleteBrand(brandName);
    }

}