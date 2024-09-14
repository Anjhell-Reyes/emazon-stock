package bootcamp.emazon.stock.infrastructure.exceptionHandler;

public enum ExceptionResponse {

    CATEGORY_NOT_FOUND("Category not found"),
    BRAND_NOT_FOUND("Brand not found"),
    ARTICLE_NOT_FOUND("Article not found"),
    CATEGORY_ALREADY_EXISTS("Category already exists"),
    BRAND_ALREADY_EXISTS("Brand already exists"),
    ARTICLE_ALREADY_EXISTS("Article already exists"),
    NO_DATA_FOUND("No data found"),
    NAME_NULL("Name must not be null"),
    DESCRIPTION_NULL("Description must not be null"),
    NAME_MAX_LENGHT("Name must be 50 characters or less"),
    DESCRIPTION_MAX_LENGHT("Description must be 90 characters or less"),
    DESCRIPTION_MAX_LENGHT_BRAND("Description must be 120 characters or less"),
    PAGE_INVALID("CustomPage index must not be less than zero"),
    INVALID_NUMBER_CATEGORIES("The article must have between 1 and 3 categories"),
    DUPLICATE_CATEGORIES("The article cannot have repeated categories");



    private String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
