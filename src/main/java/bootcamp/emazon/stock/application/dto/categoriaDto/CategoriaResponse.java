package bootcamp.emazon.stock.application.dto.categoriaDto;

import lombok.Data;

@Data
public class CategoriaResponse {
    private Long id;
    private String nombre;
    private String descripcion;

}
