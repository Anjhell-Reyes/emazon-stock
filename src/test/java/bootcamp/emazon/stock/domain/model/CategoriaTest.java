package bootcamp.emazon.stock.domain.model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CategoriaTest {

    @Test
    void testCategoriaConstructor() {
        Long id = 1L;
        String nombre = "Electrónica";
        String descripcion = "Productos electrónicos de todo tipo";

        Categoria categoria = new Categoria(id, nombre, descripcion);

        assertEquals(id, categoria.getId());
        assertEquals(nombre, categoria.getNombre());
        assertEquals(descripcion, categoria.getDescripcion());
    }

    @Test
    void testSettersAndGetters() {
        Categoria categoria = new Categoria();

        Long id = 1L;
        String nombre = "Electrónica";
        String descripcion = "Productos electrónicos de todo tipo";

        categoria.setId(id);
        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);

        assertEquals(id, categoria.getId());
        assertEquals(nombre, categoria.getNombre());
        assertEquals(descripcion, categoria.getDescripcion());
    }

    @Test
    void testEmptyConstructor() {
        Categoria categoria = new Categoria();

        assertNull(categoria.getId());
        assertNull(categoria.getNombre());
        assertNull(categoria.getDescripcion());
    }
}
