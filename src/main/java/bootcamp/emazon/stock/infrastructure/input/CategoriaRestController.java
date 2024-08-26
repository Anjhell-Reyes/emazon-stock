package bootcamp.emazon.stock.infrastructure.input;

import bootcamp.emazon.stock.application.dto.categoriaDto.CategoriaRequest;
import bootcamp.emazon.stock.application.dto.categoriaDto.CategoriaResponse;
import bootcamp.emazon.stock.application.handler.categoriaHandler.ICategoriaHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaRestController {
    private final ICategoriaHandler categoriaHandler;

    @Operation(summary = "Add a new categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Categoria already exists", content = @Content)
    })

    @PostMapping
    public ResponseEntity<Void> saveCategoriaInStock(@RequestBody CategoriaRequest categoriaRequest) {
        categoriaHandler.saveCategoriaInStock(categoriaRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get a categoria by their id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoriaResponse.class))),
            @ApiResponse(responseCode = "404", description = "Categoria not found", content = @Content)
    })
    @GetMapping("/{nombre}")
    public ResponseEntity<CategoriaResponse> getAllCategoriasFromStock(@Parameter(description = "id of the categoria to be returned")
                                                                 @PathVariable(name = "nombre") String categoriaNombre) {
        return ResponseEntity.ok(categoriaHandler.getCategoriaFromStock(categoriaNombre));
    }

    @Operation(summary = "Get paginated categorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paged categorias returned",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
    })

    @GetMapping
    public ResponseEntity<Page<CategoriaResponse>> getCategoriasFromStock(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nombre") String sort,
            @RequestParam(defaultValue = "asc") String direction) {
        Page<CategoriaResponse> categorias = categoriaHandler.getAllCategoriasFromStock(page, size, sort, direction);
        return ResponseEntity.ok(categorias);
    }


    @Operation(summary = "Update an existing categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria updated", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cateogria not found", content = @Content)
    })
    @PutMapping
    public ResponseEntity<Void> updateCategoriaInStock(@RequestBody CategoriaRequest categoriaRequest) {
        categoriaHandler.updateCategoriaInStock(categoriaRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete a categoria by their id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Categoria not found", content = @Content)
    })
    @DeleteMapping("/{categoriaNombre}")
    public ResponseEntity<Void> deleteCategoriaFromStock(@PathVariable String categoriaNombre) {
        categoriaHandler.deleteCategoriaInStock(categoriaNombre);
        return ResponseEntity.noContent().build();
    }
}

