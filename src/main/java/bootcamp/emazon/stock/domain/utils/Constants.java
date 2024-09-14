package bootcamp.emazon.stock.domain.utils;

public final class Constants {

    //Controller Paginated
    public static final String DEFAULT_PAGE = "1";
    public static final String DEFAULT_SIZE = "10";
    public static final String DEFAULT_SORT_BY = "name";

    // Pagination
    public static final int MAX_CATEGORIES = 3;

    // Sort fields
    public static final String SORT_BY_BRAND_NAME = "brand.name";
    public static final String SORT_BY_CATEGORY_NAMES = "categories.name";
    public static final String SORT_BY_DEFAULT = "name";

    //Use cases
    public static final Integer MAX_LENGHT_NAME = 50;
    public static final Integer MAX_LENGHT_DESCRIPTION_CATEGORY = 90;
    public static final Integer MAX_LENGHT_DESCRIPTION_BRAND = 120;

    //Dto
    public static final String NAME_NOT_BLANK_MESSAGE = "The name cannot be blank";
    public static final String DESCRIPTION_NOT_BLANK_MESSAGE = "The description cannot be blank";
    public static final String QUANTITY_NOT_NULL_MESSAGE = "The quantity cannot be null";
    public static final String QUANTITY_MIN_MESSAGE = "Quantity must be at least 1";
    public static final String PRICE_NOT_NULL_MESSAGE = "The price cannot be null";
    public static final String PRICE_POSITIVE_MESSAGE = "Price must be greater than zero";
    public static final String BRAND_NOT_BLANK_MESSAGE = "The brand cannot be blank";
    public static final String CATEGORIES_NOT_NULL_MESSAGE = "The categories cannot be null";
    public static final String CATEGORIES_SIZE_MESSAGE = "The categories should have between 1 and 3 elements";

    public static final String NAME_NOT_NULL_MESSAGE = "The name cannot be null";
    public static final String NAME_NOT_EMPTY_MESSAGE = "The name cannot be empty";
    public static final String NAME_SIZE_MESSAGE = "The name cannot be more than 50 characters";

    public static final String DESCRIPTION_NOT_NULL_MESSAGE = "The description cannot be null";
    public static final String DESCRIPTION_NOT_EMPTY_MESSAGE = "Description cannot be empty";
    public static final String DESCRIPTION_SIZE_MESSAGE_BRAND = "The description cannot be more than 120 characters";

    public static final String DESCRIPTION_SIZE_MESSAGE_CATEGORY = "The description cannot be more than 90 characters";


    private Constants() {

    }
}
