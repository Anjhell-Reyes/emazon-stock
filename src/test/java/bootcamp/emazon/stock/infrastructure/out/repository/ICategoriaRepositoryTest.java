package bootcamp.emazon.stock.infrastructure.out.repository;
import bootcamp.emazon.stock.infrastructure.out.entity.CategoriaEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) // Usa una base de datos en memoria
public class ICategoriaRepositoryTest {

    @Autowired
    private ICategoriaRepository categoriaRepository;

    @Test
    @Transactional
     void whenFindByNombre_thenReturnCategoriaEntity() {
        // Arrange
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setNombre("Electronics");
        categoriaEntity.setDescripcion("Description");
        categoriaRepository.save(categoriaEntity);

        // Act
        Optional<CategoriaEntity> found = categoriaRepository.findByNombre("Electronics");

        // Assert
        assertThat(found).isPresent();
        assertThat(found.get().getNombre()).isEqualTo("Electronics");
        assertThat(found.get().getDescripcion()).isEqualTo("Description");
    }

    @Test
    @Transactional
     void whenDeleteByNombre_thenCategoriaEntityShouldBeDeleted() {
        // Arrange
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setNombre("Books");
        categoriaEntity.setDescripcion("Books Description");
        categoriaRepository.save(categoriaEntity);

        // Act
        categoriaRepository.deleteByNombre("Books");

        // Assert
        Optional<CategoriaEntity> found = categoriaRepository.findByNombre("Books");
        assertThat(found).isNotPresent();
    }

    @Test
    void testFindAllWithPageable() {
        // Preparar datos de prueba
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setNombre("Categoria Test");
        categoriaEntity.setDescripcion("Descripcion Test");
        categoriaRepository.save(categoriaEntity);

        // Configurar Pageable
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "nombre"));

        // Ejecutar el método
        Page<CategoriaEntity> result = categoriaRepository.findAll(pageable);

        // Verificar el resultado
        assertFalse(result.isEmpty(), "La página no debe estar vacía");
        assertEquals(1, result.getTotalElements(), "Debe haber un elemento en el resultado");
        assertEquals("Categoria Test", result.getContent().get(0).getNombre(), "El nombre debe coincidir");
    }
}
