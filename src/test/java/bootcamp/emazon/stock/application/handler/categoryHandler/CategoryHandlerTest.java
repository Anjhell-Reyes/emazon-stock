package bootcamp.emazon.stock.application.handler.categoryHandler;

import bootcamp.emazon.stock.application.dto.categoryDto.CategoryRequest;
import bootcamp.emazon.stock.application.dto.categoryDto.CategoryResponse;
import bootcamp.emazon.stock.application.mapper.CategoryMapper;
import bootcamp.emazon.stock.domain.api.ICategoryServicePort;
import bootcamp.emazon.stock.domain.model.Category;
import bootcamp.emazon.stock.domain.pagination.CategoryPaginated;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
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
    void testGetAllCategoriesFromStock() {
        // Datos simulados
        int page = 0;
        int size = 10;
        String sortBy = "name";
        boolean asc = true;

        CategoryPaginated category1 = new CategoryPaginated(1L, "Category 1", "Description 1");
        CategoryPaginated category2 = new CategoryPaginated(2L, "Category 2", "Description 2");

        List<CategoryPaginated> expectedCategories = Arrays.asList(category1, category2);

        // Simulación del comportamiento del mock
        when(categoryServicePort.getAllCategories(page, size, sortBy, asc)).thenReturn(expectedCategories);

        // Llamada al método a probar
        List<CategoryPaginated> actuallyCategories = categoryHandler.getAllCategoriesFromStock(page, size, sortBy, asc);

        // Verificación de resultados
        assertEquals(expectedCategories, actuallyCategories);
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