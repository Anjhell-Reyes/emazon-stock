package bootcamp.emazon.stock.application.dto.categoryDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {
    @NotBlank(message = "The name cannot be blank")
    @NotNull(message = "The name cannot be null")
    @NotEmpty(message = "The name cannot be empty")
    @Size(max = 50, message = "The name cannot be more than 50 characters")
    private String name;

    @NotBlank(message = "The description cannot be blank")
    @NotNull(message = "The description cannot be null")
    @NotEmpty(message = "Description cannot be empty")
    @Size(max = 90, message = "The description cannot be more than 90 characters")
    private String description;
}
