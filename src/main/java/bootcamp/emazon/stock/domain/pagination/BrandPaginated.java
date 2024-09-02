package bootcamp.emazon.stock.domain.pagination;

public class BrandPaginated {
    private Long id;
    private String name;
    private String description;

    // Constructor vacío
    public BrandPaginated() {
    }

    // Constructor con todos los campos
    public BrandPaginated(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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