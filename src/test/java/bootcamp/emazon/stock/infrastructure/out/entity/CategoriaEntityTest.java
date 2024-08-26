package bootcamp.emazon.stock.infrastructure.out.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

    @SpringBootTest
    public class CategoriaEntityTest {

        @Test
         void testEntityMapping() {
            CategoriaEntity categoriaEntity = new CategoriaEntity();
            categoriaEntity.setNombre("Electronics");
            categoriaEntity.setDescripcion("Devices and gadgets");

            assertEquals("Electronics", categoriaEntity.getNombre());
            assertEquals("Devices and gadgets", categoriaEntity.getDescripcion());
        }
    }
