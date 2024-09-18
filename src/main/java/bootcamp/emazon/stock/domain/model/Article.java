    package bootcamp.emazon.stock.domain.model;

    import java.util.List;

    public class Article {
        private Long id;
        private String name;
        private String description;
        private Integer quantity;
        private double price;
        private Brand brand;
        private List<Category> categories;

        // Constructor vacío
        public Article() {
        }

        // Constructor con todos los campos
        public Article(Long id, String name, String description, Integer quantity, Double price, Brand brand) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.quantity = quantity;
            this.price = price;
            this.brand = brand;
        }

        // Tests
            public Article(Long id, String name, String description, Integer quantity, Double price, Brand brand, List<Category> categories) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.quantity = quantity;
            this.price = price;
            this.brand = brand;
            this.categories = categories;
        }

        public Article(Long id, String name, String description, Integer quantity, Double price) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.quantity = quantity;
            this.price = price;
        }

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

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public Brand getBrand() {
            return brand;
        }

        public void setBrand(Brand brand) {
            this.brand = brand;
        }

        public List<Category> getCategories() {
            return categories;
        }

        public void setCategories(List<Category> categories) {
            this.categories = categories;
        }
    }
