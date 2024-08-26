package bootcamp.emazon.stock.domain.usecase;
import bootcamp.emazon.stock.application.handler.categoriaHandler.CategoriaHandler;
import bootcamp.emazon.stock.application.mapper.CategoriaMapper;
import bootcamp.emazon.stock.domain.api.ICategoriaServicePort;
import bootcamp.emazon.stock.domain.model.Categoria;
import bootcamp.emazon.stock.domain.spi.ICategoriaPersistencePort;
import bootcamp.emazon.stock.infrastructure.exception.CategoriaNotFoundException;
import bootcamp.emazon.stock.infrastructure.out.entity.CategoriaEntity;
import bootcamp.emazon.stock.infrastructure.out.mapper.CategoriaEntityMapper;
import bootcamp.emazon.stock.infrastructure.out.repository.ICategoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class CategoriaUseCaseIntegrationTest {

    @Autowired
    private ICategoriaPersistencePort categoriaPersistencePort;

    @Autowired
    private ICategoriaServicePort categoriaServicePort;

    @Autowired
    private ICategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaEntityMapper categoriaEntityMapper;

    @Autowired
    private CategoriaHandler categoriaHandler;

    @MockBean
    private CategoriaMapper categoriaMapper;

    private CategoriaUseCase categoriaUseCase;

    @BeforeEach
    void setUp() {
        categoriaUseCase = new CategoriaUseCase(categoriaPersistencePort);
    }

    @Test
    void testSaveCategoria() {
        Categoria categoria = new Categoria(1L,"Electronics","All kinds of electronics");

        Categoria savedCategoria = categoriaUseCase.saveCategoria(categoria);

        assertThat(savedCategoria.getId()).isNotNull();
        assertThat(savedCategoria.getNombre()).isEqualTo("Electronics");
    }

    @Test
    void testGetCategoriaByName() {
        Categoria categoria = new Categoria();
        categoria.setNombre("Electronics");
        categoria.setDescripcion("All kinds of electronics");
        categoriaUseCase.saveCategoria(categoria);

        Categoria foundCategoria = categoriaUseCase.getCategoria("Electronics");

        assertThat(foundCategoria).isNotNull();
        assertThat(foundCategoria.getNombre()).isEqualTo("Electronics");
    }

    @Test
    void getAllCategorias_shouldReturnPaginatedCategorias() {
        // Arrange
        Categoria categoria1 = new Categoria();
        categoria1.setId(1L);
        categoria1.setNombre("Categoria1");
        categoria1.setDescripcion("Descripcion1");
        CategoriaEntity categoriaEntity1 = categoriaEntityMapper.toEntity(categoria1);
        Categoria categoria2 = new Categoria();
        categoria2.setId(2L);
        categoria2.setNombre("Categoria2");
        categoria2.setDescripcion("Descripcion2");
        CategoriaEntity categoriaEntity2 = categoriaEntityMapper.toEntity(categoria2);
        categoriaRepository.save(categoriaEntity1);
        categoriaRepository.save(categoriaEntity2);
        int page = 0;
        int size = 10;
        String sort = "nombre";
        String direction = "ASC";

        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));

        // Act
        Page<Categoria> result = categoriaServicePort.getAllCategorias(pageable);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent().get(0).getNombre()).isEqualTo("Categoria1");
        assertThat(result.getContent().get(1).getNombre()).isEqualTo("Categoria2");
    }

    @Test
    void testUpdateCategoria() {
        Categoria categoria = new Categoria();
        categoria.setNombre("Electronics");
        categoria.setDescripcion("All kinds of electronics");
        categoriaUseCase.saveCategoria(categoria);

        Categoria updatedCategoria = new Categoria();
        updatedCategoria.setNombre("Electronics");
        updatedCategoria.setDescripcion("Updated description");

        assertThrows(RuntimeException.class, () -> {
            categoriaUseCase.updateCategoria(updatedCategoria);
        });
    }

    @Test
    void testDeleteCategoria() {
        // Arrange: Crear y guardar una nueva categoría
        Categoria categoria = new Categoria();
        categoria.setNombre("Electronics");
        categoria.setDescripcion("All kinds of electronics");
        categoriaUseCase.saveCategoria(categoria);

        // Act: Eliminar la categoría por nombre
        categoriaUseCase.deleteCategoria("Electronics");

        // Assert: Verificar que la categoría ha sido eliminada
        assertThrows(CategoriaNotFoundException.class, () -> {
            categoriaUseCase.getCategoria("Electronics");
        });
    }
}
