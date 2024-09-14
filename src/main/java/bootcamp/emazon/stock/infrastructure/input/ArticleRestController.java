package bootcamp.emazon.stock.infrastructure.input;

import bootcamp.emazon.stock.application.dto.articleDto.ArticlePaginated;
import bootcamp.emazon.stock.application.dto.articleDto.ArticleRequest;
import bootcamp.emazon.stock.application.dto.articleDto.ArticleResponse;
import bootcamp.emazon.stock.application.handler.articleHandler.IArticleHandler;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleRestController {
    private final IArticleHandler articleHandler;

    @Operation(summary = "Add a new article")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Article created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Article already exists", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Void> saveArticleInStock(@RequestBody ArticleRequest articleRequest) {
        articleHandler.saveArticleInStock(articleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get an article by its name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ArticleResponse.class))),
            @ApiResponse(responseCode = "404", description = "Article not found", content = @Content)
    })
    @GetMapping("/{articleName}")
    public ResponseEntity<ArticleResponse> getArticleFromStock(@Parameter(description = "Name of the article to be returned")
                                                               @PathVariable(name = "articleName") String articleName) {
        return ResponseEntity.ok(articleHandler.getArticleFromStock(articleName));
    }

    @Operation(summary = "Get paginated articles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paged articles returned",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
    })
    @GetMapping
    public ResponseEntity<Page<ArticlePaginated>> getArticlesFromStock(
            @RequestParam(defaultValue = Constants.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = Constants.DEFAULT_SIZE) int size,
            @RequestParam(defaultValue = Constants.DEFAULT_SORT_BY) String sortBy,
            @RequestParam(defaultValue = "true") boolean asc) {
        Page<ArticlePaginated> articles = articleHandler.getAllArticlesFromStock(page, size, sortBy, asc);
        return ResponseEntity.ok(articles);
    }

    @Operation(summary = "Update an existing article")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article updated", content = @Content),
            @ApiResponse(responseCode = "404", description = "Article not found", content = @Content)
    })
    @PutMapping
    public ResponseEntity<Void> updateArticleInStock(@RequestBody ArticleRequest articleRequest) {
        articleHandler.updateArticleInStock(articleRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete an article by its name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Article not found", content = @Content)
    })
    @DeleteMapping("/{articleName}")
    public ResponseEntity<Void> deleteArticleFromStock(@PathVariable String articleName) {
        articleHandler.deleteArticleInStock(articleName);
        return ResponseEntity.noContent().build();
    }
}
