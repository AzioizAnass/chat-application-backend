package com.blog.controller;

import com.blog.dto.ArticleDto;
import com.blog.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ArticleIntegrationTest {

    @Mock
    private ArticleService articleService;

    @InjectMocks
    private ArticleResource articleResource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll_ShouldReturnListOfArticles() {
        // Arrange
        ArticleDto article1 = new ArticleDto();
        article1.setId(1L);
        article1.setTitle("Title 1");
        article1.setContent("Content 1");
        article1.setCreationDate("2025-01-01");
        article1.setComments(Collections.emptyList());

        ArticleDto article2 = new ArticleDto();
        article2.setId(2L);
        article2.setTitle("Title 2");
        article2.setContent("Content 2");
        article2.setCreationDate("2025-01-02");
        article2.setComments(Collections.emptyList());

        List<ArticleDto> mockArticles = Arrays.asList(article1, article2);

        when(articleService.getAllArticles()).thenReturn(mockArticles);

        // Act
        ResponseEntity<List<ArticleDto>> response = articleResource.getAll();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(articleService, times(1)).getAllArticles();
    }

    @Test
    void getArticleById_ShouldReturnArticle() {
        // Arrange
        Long articleId = 1L;
        ArticleDto mockArticle = new ArticleDto();
        mockArticle.setId(articleId);
        mockArticle.setTitle("Title");
        mockArticle.setContent("Content");
        mockArticle.setCreationDate("2025-01-01");
        mockArticle.setComments(Collections.emptyList());

        when(articleService.getById(articleId)).thenReturn(mockArticle);

        // Act
        ResponseEntity<ArticleDto> response = articleResource.getArticleById(articleId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockArticle, response.getBody());
        verify(articleService, times(1)).getById(articleId);
    }

    @Test
    void createArticle_ShouldReturnCreatedArticle() {
        // Arrange
        ArticleDto newArticle = new ArticleDto();
        newArticle.setTitle("New Title");
        newArticle.setContent("New Content");
        newArticle.setCreationDate("2025-01-03");
        newArticle.setComments(Collections.emptyList());

        ArticleDto createdArticle = new ArticleDto();
        createdArticle.setId(1L);
        createdArticle.setTitle("New Title");
        createdArticle.setContent("New Content");
        createdArticle.setCreationDate("2025-01-03");
        createdArticle.setComments(Collections.emptyList());

        when(articleService.createArticle(newArticle)).thenReturn(createdArticle);

        // Act
        ResponseEntity<ArticleDto> response = articleResource.createArticle(newArticle);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(createdArticle, response.getBody());
        verify(articleService, times(1)).createArticle(newArticle);
    }

    @Test
    void updateArticle_ShouldReturnUpdatedArticle() {
        // Arrange
        Long articleId = 1L;
        ArticleDto updatedArticleDto = new ArticleDto();
        updatedArticleDto.setId(articleId);
        updatedArticleDto.setTitle("Updated Title");
        updatedArticleDto.setContent("Updated Content");
        updatedArticleDto.setCreationDate("2025-01-04");
        updatedArticleDto.setComments(Collections.emptyList());

        when(articleService.updateArticle(articleId, updatedArticleDto)).thenReturn(updatedArticleDto);

        // Act
        ResponseEntity<ArticleDto> response = articleResource.updateArticle(articleId, updatedArticleDto);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedArticleDto, response.getBody());
        verify(articleService, times(1)).updateArticle(articleId, updatedArticleDto);
    }

    @Test
    void deleteArticle_ShouldReturnNoContent() {
        // Arrange
        Long articleId = 1L;

        doNothing().when(articleService).deleteArticle(articleId);

        // Act
        ResponseEntity<Void> response = articleResource.deleteArticle(articleId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        verify(articleService, times(1)).deleteArticle(articleId);
    }
}
