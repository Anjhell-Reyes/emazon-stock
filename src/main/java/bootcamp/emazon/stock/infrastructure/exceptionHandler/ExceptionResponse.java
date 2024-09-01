package bootcamp.emazon.stock.infrastructure.exceptionHandler;

public enum ExceptionResponse {

    CATEGORIA_NOT_FOUND("Category not found"),
    CATEGORIA_ALREADY_EXISTS("Category already exists"),
    NO_DATA_FOUND("No data found"),
    NAME_NULL("Name must not be null"),
    DESCRIPTION_NULL("Description must not be null"),
    NAME_MAX_LENGHT("Name must be 50 characters or less"),
    DESCRIPTION_MAX_LENGHT("Description must be 90 characters or less"),
    PAGE_INVALID("Page index must not be less than zero");



    private String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
