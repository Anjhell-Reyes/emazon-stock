package bootcamp.emazon.stock.infrastructure.out.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
public class CategoryEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true, nullable = false, length = 50)
        private String name;

        @Column(nullable = false, length = 90)
        private String description;

        @ManyToMany(mappedBy = "categories")
        private Set<ArticleEntity> articles = new HashSet<>();

}
