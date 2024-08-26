package bootcamp.emazon.stock.infrastructure.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CategoriaAlreadyExistsExeptionTest {

    @Test
    public void testCategoriaAlreadyExistsExeption() {
        // Verifica que la excepción se lanza cuando se crea una instancia de CategoriaAlreadyExistsExeption
        assertThrows(CategoriaAlreadyExistsExeption.class, () -> {
            throw new CategoriaAlreadyExistsExeption();
        });
    }
}
