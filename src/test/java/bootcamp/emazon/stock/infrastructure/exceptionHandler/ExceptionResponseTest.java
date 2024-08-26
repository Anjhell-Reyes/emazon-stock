package bootcamp.emazon.stock.infrastructure.exceptionHandler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExceptionResponseTest {

    @Test
    public void testCategoriaNotFoundMessage() {
        assertEquals("Category not found", ExceptionResponse.CATEGORIA_NOT_FOUND.getMessage());
    }

    @Test
    public void testCategoriaAlreadyExistsMessage() {
        assertEquals("Category already exists", ExceptionResponse.CATEGORIA_ALREADY_EXISTS.getMessage());
    }

    @Test
    public void testNoDataFoundMessage() {
        assertEquals("No data found", ExceptionResponse.NO_DATA_FOUND.getMessage());
    }

    @Test
    public void testNameOrLengthMessage() {
        assertEquals("Name must not be null and must be 50 characters or less", ExceptionResponse.NAME_OR_LENGHT.getMessage());
    }

    @Test
    public void testDescriptionNullLengthMessage() {
        assertEquals("Description must not be null and must be 90 characters or less", ExceptionResponse.DESCRIPTION_NULL_LENGHT.getMessage());
    }
}
