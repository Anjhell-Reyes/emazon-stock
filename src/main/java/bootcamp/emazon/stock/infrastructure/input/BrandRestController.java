package bootcamp.emazon.stock.infrastructure.input;

import bootcamp.emazon.stock.application.dto.brandDto.BrandRequest;
import bootcamp.emazon.stock.application.dto.brandDto.BrandResponse;
import bootcamp.emazon.stock.application.handler.brandHandler.IBrandHandler;
import bootcamp.emazon.stock.application.dto.brandDto.BrandPaginated;
import bootcamp.emazon.stock.domain.utils.Constants;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
public class BrandRestController {
    private final IBrandHandler brandHandler;

    @Operation(summary = "Add a new brand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Brand created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Brand already exists", content = @Content)
    })
    @PreAuthorize("hasAuthority('admin')")
    @PostMapping
    public ResponseEntity<Void> saveBrandInStock(@RequestBody BrandRequest brandRequest) {
        brandHandler.saveBrandInStock(brandRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get a brand by their name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BrandResponse.class))),
            @ApiResponse(responseCode = "404", description = "Brand not found", content = @Content)
    })
    @PreAuthorize("hasAnyAuthority('aux_bodega', 'admin', 'customer')")
    @GetMapping("/{brandName}")
    public ResponseEntity<BrandResponse> getBrandFromStock(@Parameter(description = "id of the brand to be returned")
                                                           @PathVariable(name = "brandName") String brandName) {
        return ResponseEntity.ok(brandHandler.getBrandFromStock(brandName));
    }

    @Operation(summary = "Get paginated brands")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paged brands returned",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
    })
    @PreAuthorize("hasAnyAuthority('aux_bodega', 'admin', 'customer')")
    @GetMapping
    public ResponseEntity<Page<BrandPaginated>> getBrandsFromStock(
            @RequestParam(defaultValue = Constants.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = Constants.DEFAULT_SIZE) int size,
            @RequestParam(defaultValue = Constants.DEFAULT_SORT_BY) String sortBy,
            @RequestParam(defaultValue = "true") boolean asc) {
        Page<BrandPaginated> brands = brandHandler.getAllBrandsFromStock(page, size, sortBy, asc);
        return ResponseEntity.ok(brands);
    }

    @Operation(summary = "Update an existing brand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand updated", content = @Content),
            @ApiResponse(responseCode = "404", description = "Brand not found", content = @Content)
    })
    @PreAuthorize("hasAuthority('aux_bodega')")
    @PutMapping
    public ResponseEntity<Void> updateBrandInStock(@RequestBody BrandRequest brandRequest) {
        brandHandler.updateBrandInStock(brandRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete a brand by their id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Brand not found", content = @Content)
    })
    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping("/{brandName}")
    public ResponseEntity<Void> deleteBrandFromStock(@PathVariable String brandName) {
        brandHandler.deleteBrandInStock(brandName);
        return ResponseEntity.noContent().build();
    }

}
