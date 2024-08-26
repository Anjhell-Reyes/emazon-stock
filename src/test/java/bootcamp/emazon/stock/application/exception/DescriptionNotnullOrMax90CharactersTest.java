package bootcamp.emazon.stock.application.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DescriptionNotnullOrMax90CharactersTest {
    @Test
    void testDescriptionNotnullOrMax50Characters() {
        assertThrows(DescriptionNotnullOrMax90Characters.class, () -> {
            throw new DescriptionNotnullOrMax90Characters();
        });
    }
}