package com.blog.controller;

import com.blog.dto.ArticleDto;
import com.blog.entity.Article;
import com.blog.mapper.ArticleMapper;
import com.blog.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/articles")
@Tag(name = "Article Controller", description = "API endpoints for article management")
@AllArgsConstructor
public class ArticleResource {
    private final ArticleService articleService;
    private final ArticleMapper articleMapper;

    @Operation(summary = "Get all articles", description = "Returns a list of all articles")
    @ApiResponse(responseCode = "200", description = "List of articles retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ArticleDto.class)))
    @GetMapping
    public ResponseEntity<List<ArticleDto>> getAll() {
        return ResponseEntity.ok(articleService.getAllArticles());
    }

    @Operation(summary = "Get article by ID", description = "Returns a single article by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ArticleDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Article not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> getArticleById(
            @Parameter(description = "ID of the article to retrieve") @PathVariable Long id) {
        return ResponseEntity.ok(articleService.getById(id));
    }

    @Operation(summary = "Create a new article", description = "Creates a new article with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Article created successfully",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ArticleDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping
    public ResponseEntity<ArticleDto> createArticle(@Valid @RequestBody ArticleDto articleDto) {
        return ResponseEntity.ok(articleService.createArticle(articleDto));
    }

    @Operation(summary = "Update article", description = "Updates an existing article")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article updated successfully"),
            @ApiResponse(responseCode = "404", description = "Article not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ArticleDto> updateArticle(
            @Parameter(description = "ID of the article to update") @PathVariable Long id,
            @Valid @RequestBody ArticleDto articleDto) {
        Article article = articleMapper.toEntity(articleDto);
        Article updatedArticle = articleService.updateArticle(id, article);
        return updatedArticle != null
                ? ResponseEntity.ok(articleMapper.toDto(updatedArticle))
                : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete article", description = "Deletes an article by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Article deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Article not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(
            @Parameter(description = "ID of the article to delete") @PathVariable Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.ok().build();
    }
}
