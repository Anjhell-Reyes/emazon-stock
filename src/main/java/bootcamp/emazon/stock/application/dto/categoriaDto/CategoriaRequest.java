package bootcamp.emazon.stock.application.dto.categoriaDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaRequest {
    @NotEmpty(message = "El nombre no puede estar vacío")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    private String nombre;

    @NotEmpty(message = "La descripción no puede estar vacía")
    @Size(max = 90, message = "La descripción no puede tener más de 90 caracteres")
    private String descripcion;
}
