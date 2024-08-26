package bootcamp.emazon.stock.infrastructure.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class NoDataFoundExceptionTest {
    @Test
    public void testNoDataFoundExceptionTest() {
        // Verifica que la excepción se lanza cuando se crea una instancia de CategoriaAlreadyExistsExeption
        assertThrows(NoDataFoundException.class, () -> {
            throw new NoDataFoundException();
        });
    }
}
