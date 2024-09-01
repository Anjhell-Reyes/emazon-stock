package bootcamp.emazon.stock.infrastructure.input;

import bootcamp.emazon.stock.application.dto.categoriaDto.CategoryRequest;
import bootcamp.emazon.stock.application.dto.categoriaDto.CategoryResponse;
import bootcamp.emazon.stock.application.handler.categoriaHandler.ICategoryHandler;
import bootcamp.emazon.stock.domain.pagination.CategoryPaginated;
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

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryRestController {
    private final ICategoryHandler categoryHandler;

    @Operation(summary = "Add a new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Category already exists", content = @Content)
    })

    @PostMapping
    public ResponseEntity<Void> saveCategoryInStock(@RequestBody CategoryRequest categoryRequest) {
        categoryHandler.saveCategoryInStock(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get a category by their id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content)
    })
    @GetMapping("/{categoryName}")
    public ResponseEntity<CategoryResponse> getCategoryFromStock(@Parameter(description = "id of the categoria to be returned")
                                                                 @PathVariable(name = "categoryName") String categoryName) {
        return ResponseEntity.ok(categoryHandler.getCategoryFromStock(categoryName));
    }

    @Operation(summary = "Get paginated categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paged categories returned",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<CategoryPaginated>> getCategoriesFromStock(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "true") boolean asc) {
        List<CategoryPaginated> categories = categoryHandler.getAllCategoriesFromStock(page, size, sortBy, asc);
        return ResponseEntity.ok(categories);
    }

    @Operation(summary = "Update an existing category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cateogry not found", content = @Content)
    })
    @PutMapping
    public ResponseEntity<Void> updateCategoryInStock(@RequestBody CategoryRequest categoryRequest) {
        categoryHandler.updateCategoryInStock(categoryRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete a category by their id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content)
    })
    @DeleteMapping("/{categoryName}")
    public ResponseEntity<Void> deleteCategoriaFromStock(@PathVariable String categoryName) {
        categoryHandler.deleteCategoryInStock(categoryName);
        return ResponseEntity.noContent().build();
    }
}

