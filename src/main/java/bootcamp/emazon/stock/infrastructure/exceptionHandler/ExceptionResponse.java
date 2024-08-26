package bootcamp.emazon.stock.infrastructure.exceptionHandler;

public enum ExceptionResponse {

    CATEGORIA_NOT_FOUND("Category not found"),
    CATEGORIA_ALREADY_EXISTS("Category already exists"),
    NO_DATA_FOUND("No data found"),
    NAME_OR_LENGHT("Name must not be null and must be 50 characters or less"),
    DESCRIPTION_NULL_LENGHT("Description must not be null and must be 50 characters or less");

    private String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
