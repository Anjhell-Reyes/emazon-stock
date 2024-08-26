package bootcamp.emazon.stock.infrastructure.exceptionHandler;

import bootcamp.emazon.stock.application.exception.DescriptionNotnullOrMax90Characters;
import bootcamp.emazon.stock.application.exception.NamenotnullOrMax50Characters;
import bootcamp.emazon.stock.infrastructure.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    private static final String MESSAGE = "Message";

    @ExceptionHandler(CategoriaAlreadyExistsExeption.class)
    public ResponseEntity<Map<String, String>> handleCategoriaAlreadyExistsException(
            CategoriaAlreadyExistsExeption exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.CATEGORIA_ALREADY_EXISTS.getMessage()));
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoDataFoundException(
            NoDataFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.NO_DATA_FOUND.getMessage()));
    }

    @ExceptionHandler(CategoriaNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCategoriaNotFoundException(
            CategoriaNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.CATEGORIA_NOT_FOUND.getMessage()));
    }

    @ExceptionHandler(NamenotnullOrMax50Characters.class)
    public ResponseEntity<Map<String, String>> handleNameNotNullOrMax50Characters(
            NamenotnullOrMax50Characters exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.NAME_OR_LENGHT.getMessage()));
    }

    @ExceptionHandler(DescriptionNotnullOrMax90Characters.class)
    public ResponseEntity<Map<String, String>> handleDescriptionNotNullOrMax50Characters(
            DescriptionNotnullOrMax90Characters exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.DESCRIPTION_NULL_LENGHT.getMessage()));
    }
}
