package bootcamp.emazon.stock.infrastructure.exceptionHandler;

import bootcamp.emazon.stock.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    private static final String MESSAGE = "Message";
    private static final String AVAILABLE_BRANDS = "availableBrands";


    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleCategoryAlreadyExistsException(
            CategoryAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.CATEGORY_ALREADY_EXISTS.getMessage()));
    }

    @ExceptionHandler(DescriptionMax120CharactersException.BrandAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleBrandAlreadyExistsException(
            DescriptionMax120CharactersException.BrandAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.BRAND_ALREADY_EXISTS.getMessage()));
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoDataFoundException(
            NoDataFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.ARTICLE_ALREADY_EXISTS.getMessage()));
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCategoryNotFoundException(
            CategoryNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.CATEGORY_NOT_FOUND.getMessage()));
    }

    @ExceptionHandler(BrandNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleBrandNotFoundException(
            BrandNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.BRAND_NOT_FOUND.getMessage()));
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleArticleNotFoundException(
            ArticleNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.ARTICLE_NOT_FOUND.getMessage()));
    }

    @ExceptionHandler(NamenotnullException.class)
    public ResponseEntity<Map<String, String>> handleNamenotnullException(
            NamenotnullException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.NAME_NULL.getMessage()));
    }

    @ExceptionHandler(DescriptionNotnullException.class)
    public ResponseEntity<Map<String, String>> handleDescriptionNotnullException(
            DescriptionNotnullException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.DESCRIPTION_NULL.getMessage()));
    }

    @ExceptionHandler(NameMax50CharactersException.class)
    public ResponseEntity<Map<String, String>> handleNameMax50CharactersException(
            NameMax50CharactersException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.NAME_MAX_LENGHT.getMessage()));
    }

    @ExceptionHandler(DescriptionMax90CharactersException.class)
    public ResponseEntity<Map<String, String>> handleDescriptionMax90CharactersException(
            DescriptionMax90CharactersException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.DESCRIPTION_MAX_LENGHT.getMessage()));
    }

    @ExceptionHandler(DescriptionMax120CharactersException.class)
    public ResponseEntity<Map<String, String>> handleDescriptionMax120CharactersException(
            DescriptionMax120CharactersException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.DESCRIPTION_MAX_LENGHT_BRAND.getMessage()));
    }

    @ExceptionHandler(InvalidPageIndexException.class)
    public ResponseEntity<Map<String, String>> handleInvalidPageIndexException(
            InvalidPageIndexException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.PAGE_INVALID.getMessage()));
    }

    @ExceptionHandler(InvalidNumberOfCategoriesException.class)
    public ResponseEntity<Map<String, String>> handleInvalidNumberOfCategoriesException(
            InvalidNumberOfCategoriesException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.INVALID_NUMBER_CATEGORIES.getMessage()));
    }

    @ExceptionHandler(DuplicateCategoriesException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateCategoriesException(
            DuplicateCategoriesException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.DUPLICATE_CATEGORIES.getMessage()));
    }

    @ExceptionHandler(ArticleAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleArticleAlreadyExistsException(
            ArticleAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.DUPLICATE_CATEGORIES.getMessage()));
    }

    @ExceptionHandler(AvailableBrandsException.class)
    public ResponseEntity<Map<String, Object>> handleAvailableBrandsException(
            AvailableBrandsException exception) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put(MESSAGE, ExceptionResponse.BRAND_NOT_FOUND.getMessage());
        responseBody.put(AVAILABLE_BRANDS, exception.getAvailableBrands());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
    }
}
