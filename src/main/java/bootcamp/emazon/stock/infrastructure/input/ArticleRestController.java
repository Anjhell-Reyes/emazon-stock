package bootcamp.emazon.stock.infrastructure.input;

import bootcamp.emazon.stock.application.dto.articleDto.ArticleRequest;
import bootcamp.emazon.stock.application.dto.brandDto.BrandRequest;
import bootcamp.emazon.stock.application.handler.articleHandler.IArticleHandler;
import bootcamp.emazon.stock.application.handler.brandHandler.IBrandHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}
