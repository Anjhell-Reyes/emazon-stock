package bootcamp.emazon.stock.application.dto.brandDto;

public class BrandPaginated {
    private String name;
    private String description;

    // Constructor vacío
    public BrandPaginated() {
    }

    // Constructor con todos los campos
    public BrandPaginated(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters y Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
