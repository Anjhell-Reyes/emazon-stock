package bootcamp.emazon.stock.domain.model;

public class Category {
    private Long id;
    private String name;
    private String description;

    public  Category(){
    }

    public Category(Long id, String name, String description) {
        this.id = id;
        if(!name.isEmpty() && name.length() < 50) {
            this.name = name;
        }
        if(!description.isEmpty() && description.length() < 90) {
            this.description = description;
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
