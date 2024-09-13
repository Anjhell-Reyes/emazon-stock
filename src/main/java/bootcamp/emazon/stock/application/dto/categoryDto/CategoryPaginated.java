package bootcamp.emazon.stock.application.dto.categoryDto;

public class CategoryPaginated {
    private String name;
    private String description;

    // Constructor vacío
    public CategoryPaginated() {
    }

    // Constructor con todos los campos
    public CategoryPaginated(String name, String description) {
        this.name = name;
        this.description = description;
    }

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

