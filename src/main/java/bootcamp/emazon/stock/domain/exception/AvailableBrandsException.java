package bootcamp.emazon.stock.domain.exception;

import java.util.List;


public class AvailableBrandsException extends RuntimeException {
    private final List<String> availableBrands;

    public AvailableBrandsException(List<String> availableBrands) {
        super();
        this.availableBrands = availableBrands;
    }

    public List<String> getAvailableBrands() {
        return availableBrands;
    }
}
