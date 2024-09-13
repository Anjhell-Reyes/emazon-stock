package bootcamp.emazon.stock.application.handler.categoryHandler;

import bootcamp.emazon.stock.application.dto.categoryDto.CategoryPaginated;
import bootcamp.emazon.stock.application.dto.categoryDto.CategoryRequest;
import bootcamp.emazon.stock.application.dto.categoryDto.CategoryResponse;
import bootcamp.emazon.stock.application.mapper.CategoryMapper;
import bootcamp.emazon.stock.domain.api.ICategoryServicePort;
import bootcamp.emazon.stock.domain.model.Category;
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
class CategoryHandlerTest {

    //Aqui con esta anotacion se define que otras clases o interfaces utiliza
    //nuestra clase a testear para poder funcionar
    @Mock
    private ICategoryServicePort categoryServicePort;

    @Mock
    private CategoryMapper categoryMapper;

    //Con esta annoracion se define a donde se van a injectar los mocks de arriba
    //por lo general es la clase a testear
    @InjectMocks
    private CategoryHandler categoryHandler;

    //Para espificar que es un test
    @Test
    //Para darle mas contexto del test
    @DisplayName("Al guardar una categoria debe llamar el mapper y el servicio")
    void saveCategoryInStock(){

        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName("TestNombre");
        categoryRequest.setDescription("TestDescripcion");

        Category category = new Category();
        category.setId(1L);
        category.setName("TestNombre");
        category.setDescription("TestDescripcion");

        //Cuando el mapper de modelo reciba un algun request, este debe retornar un modelo
        when(categoryMapper.toCategory(any(CategoryRequest.class))).thenReturn(category);

        //invocar el metodo guardar
        categoryHandler.saveCategoryInStock(categoryRequest);

        //para verificar cuantas veces se invoco el metodo
        verify(categoryMapper, times(1)).toCategory(categoryRequest);
        verify(categoryServicePort, times(1)).saveCategory(category);
    }

    @Test
    void saveCategoryInStock_ShouldCallMapperAndService() {
        CategoryRequest request = new CategoryRequest();
        request.setName("Electrónica");
        request.setDescription("Productos electrónicos");

        Category category = new Category(1L, "testName", "descriptionTest");
        when(categoryMapper.toCategory(any(CategoryRequest.class))).thenReturn(category);

        categoryHandler.saveCategoryInStock(request);

        verify(categoryMapper, times(1)).toCategory(request);
        verify(categoryServicePort, times(1)).saveCategory(category);
    }

    @Test
    void testGetCategoryFromStock() {
        // Datos simulados
        String categoryNombre = "Electrónica";

        Category category = new Category(1L, "Electrónica", "Categoría de electrónicos");
        CategoryResponse expectedResponse = new CategoryResponse();
        expectedResponse.setId(1L);
        expectedResponse.setName("Electrónica");
        expectedResponse.setName("Categoría de electrónicos");

        // Simulación del comportamiento del mock
        when(categoryServicePort.getCategory(categoryNombre)).thenReturn(category);
        when(categoryMapper.toResponse(category)).thenReturn(expectedResponse);

        // Llamada al método a probar
        CategoryResponse actuallyResponse = categoryHandler.getCategoryFromStock(categoryNombre);

        // Verificación de resultados
        assertEquals(expectedResponse, actuallyResponse);
    }

    @Test
    void testGetAllCategoriesFromStockSuccessfully() {
        // Arrange
        int page = 1;
        int size = 1;
        String sortBy = "name";
        boolean asc = true;

        Category category = new Category(1L, "Electronics", "All electronic items");
        CategoryPaginated categoryPaginated = new CategoryPaginated();
        categoryPaginated.setName("Electronics");
        categoryPaginated.setDescription("All electronic items");
        List<Category> categories = Collections.singletonList(category);

        CustomPage<Category> customPage = new CustomPage<>(categories, page, size, categories.size());

        when(categoryServicePort.getAllCategories(page, size, sortBy, asc)).thenReturn(customPage);
        when(categoryMapper.toCategoryPaginated(category)).thenReturn(categoryPaginated);

        // Act
        Page<CategoryPaginated> result = categoryHandler.getAllCategoriesFromStock(page, size, sortBy, asc);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getContent().size());
        assertEquals("Electronics", result.getContent().get(0).getName());

        verify(categoryServicePort, times(1)).getAllCategories(page, size, sortBy, asc);
        verify(categoryMapper, times(1)).toCategoryPaginated(category);
    }

    @Test
    void testGetAllCategoriesFromStockEmptyResult() {
        // Arrange
        int page = 1;
        int size = 10;
        String sortBy = "name";
        boolean asc = true;

        List<Category> categories = Collections.emptyList();
        CustomPage<Category> customPage = new CustomPage<>(categories, page, size, 0);

        when(categoryServicePort.getAllCategories(page, size, sortBy, asc)).thenReturn(customPage);

        // Act
        Page<CategoryPaginated> result = categoryHandler.getAllCategoriesFromStock(page, size, sortBy, asc);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        assertEquals(0, result.getContent().size());

        verify(categoryServicePort, times(1)).getAllCategories(page, size, sortBy, asc);
        verify(categoryMapper, never()).toCategoryPaginated(any(Category.class)); // No debería mapear nada
    }

    @Test
    void updateCategoryInStock_ShouldUpdateCategory() {
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName("Electrónica");
        categoryRequest.setDescription("Productos electrónicos");

        Category oldCategory = new Category(1L, "Electrónica", "Descripción antigua");
        Category newCategory = new Category(1L, "Electrónica", "Productos electrónicos");

        when(categoryServicePort.getCategory(categoryRequest.getName())).thenReturn(oldCategory);
        when(categoryMapper.toCategory(categoryRequest)).thenReturn(newCategory);

        categoryHandler.updateCategoryInStock(categoryRequest);

        verify(categoryServicePort, times(1)).getCategory(categoryRequest.getName());
        verify(categoryMapper, times(1)).toCategory(categoryRequest);
        verify(categoryServicePort, times(1)).updateCategory(newCategory);
    }

    @Test
    void deleteCategoryInStock_ShouldCallDelete() {
        String categoryNombre = "Electrónica";

        categoryHandler.deleteCategoryInStock(categoryNombre);

        verify(categoryServicePort, times(1)).deleteCategory(categoryNombre);
    }
}