package bootcamp.emazon.stock.application.dto.categoriaDto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoriaRequestTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenNombreIsEmpty_thenValidationFails() {
        CategoriaRequest request = new CategoriaRequest();
        request.setNombre("");
        request.setDescripcion("Una descripción válida");

        Set<ConstraintViolation<CategoriaRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        assertEquals("El nombre no puede estar vacío", violations.iterator().next().getMessage());
    }

    @Test
    void whenDescripcionIsTooLong_thenValidationFails() {
        CategoriaRequest request = new CategoriaRequest();
        request.setNombre("Un nombre válido");
        request.setDescripcion("Una descripción que es demasiado larga y excede los 90 caracteres permitidos por la anotación @Size");

        Set<ConstraintViolation<CategoriaRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        assertEquals("La descripción no puede tener más de 90 caracteres", violations.iterator().next().getMessage());
    }

    @Test
    void whenNombreAndDescripcionAreValid_thenValidationSucceeds() {
        CategoriaRequest request = new CategoriaRequest();
        request.setNombre("Un nombre válido");
        request.setDescripcion("Una descripción válida");

        Set<ConstraintViolation<CategoriaRequest>> violations = validator.validate(request);

        assertEquals(0, violations.size());
    }
}
