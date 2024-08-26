package bootcamp.emazon.stock.infrastructure.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CategoriaNotFoundExceptionTest {

    @Test
    public void testCategoriaNotFoundException() {
        // Verifica que la excepción se lanza cuando se crea una instancia de CategoriaAlreadyExistsExeption
        assertThrows(CategoriaNotFoundException.class, () -> {
            throw new CategoriaNotFoundException();
        });
    }
}

