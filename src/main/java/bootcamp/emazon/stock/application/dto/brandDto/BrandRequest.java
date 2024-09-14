package bootcamp.emazon.stock.application.dto.brandDto;

import bootcamp.emazon.stock.domain.utils.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class BrandRequest {
        @NotBlank(message = Constants.NAME_NOT_BLANK_MESSAGE)
        @NotNull(message = Constants.NAME_NOT_NULL_MESSAGE)
        @NotEmpty(message = Constants.NAME_NOT_EMPTY_MESSAGE)
        @Size(max = 50, message = Constants.NAME_SIZE_MESSAGE)
        private String name;

        @NotBlank(message = Constants.DESCRIPTION_NOT_BLANK_MESSAGE)
        @NotNull(message = Constants.DESCRIPTION_NOT_NULL_MESSAGE)
        @NotEmpty(message = Constants.DESCRIPTION_NOT_EMPTY_MESSAGE)
        @Size(max = 120, message = Constants.DESCRIPTION_SIZE_MESSAGE_BRAND)
        private String description;

}
