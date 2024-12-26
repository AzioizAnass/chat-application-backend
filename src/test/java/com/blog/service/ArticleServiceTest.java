package com.blog.service;

import com.blog.entity.Article;
import com.blog.exception.ResourceNotFoundException;
import com.blog.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleService articleService;

    private Article article;

    @BeforeEach
    void setUp() {
        article = new Article();
        article.setArticleId(1L);
        article.setTitle("Test Article");
        article.setContent("Test Content");
        article.setCreationDate("2024-12-09T21:03:44Z");
    }

    @Test
    void getAllArticles_ShouldReturnListOfArticles() {
        // Given
        List<Article> expectedArticles = Arrays.asList(article);
        when(articleRepository.findAll()).thenReturn(expectedArticles);

        // When
        List<Article> actualArticles = articleService.getAllArticles();

        // Then
        assertThat(actualArticles).isEqualTo(expectedArticles);
        verify(articleRepository).findAll();
    }

    @Test
    void getArticleById_WithValidId_ShouldReturn() {
        // Given
        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));

        // When
        Optional<Article> foundArticle = articleService.getById(1L);

        // Then
        assertThat(foundArticle).isPresent();
        assertThat(foundArticle.get()).isEqualTo(article);
        verify(articleRepository).findById(1L);
    }

    @Test
    void getById_WithInvalidId_ShouldThrowException() {
        // Given
        when(articleRepository.findById(99L)).thenReturn(Optional.empty());

        // When/Then
        assertThatThrownBy(() -> articleService.getById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Article not found with id : '99'");
    }

    @Test
    void createArticle_ShouldReturnSavedArticle() {
        // Given
        when(articleRepository.save(any(Article.class))).thenReturn(article);

        // When
        Article savedArticle = articleService.createArticle(article);

        // Then
        assertThat(savedArticle).isEqualTo(article);
        verify(articleRepository).save(article);
    }

    @Test
    void updateArticle_WithValidId_ShouldReturnUpdatedArticle() {
        // Given
        Article updatedArticle = new Article();
        updatedArticle.setTitle("Updated Title");
        updatedArticle.setContent("Updated Content");
        
        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));
        when(articleRepository.save(any(Article.class))).thenReturn(article);

        // When
        Article result = articleService.updateArticle(1L, updatedArticle);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Updated Title");
        assertThat(result.getContent()).isEqualTo("Updated Content");
        verify(articleRepository).findById(1L);
        verify(articleRepository).save(any(Article.class));
    }

    @Test
    void updateArticle_WithInvalidId_ShouldThrowException() {
        // Given
        when(articleRepository.findById(99L)).thenReturn(Optional.empty());

        // When/Then
        assertThatThrownBy(() -> articleService.updateArticle(99L, new Article()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Article not found with id : '99'");
    }

    @Test
    void deleteArticle_WithValidId_ShouldDeleteArticle() {
        // Given
        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));
        doNothing().when(articleRepository).delete(article);

        // When
        articleService.deleteArticle(1L);

        // Then
        verify(articleRepository).findById(1L);
        verify(articleRepository).delete(article);
    }

    @Test
    void deleteArticle_WithInvalidId_ShouldThrowException() {
        // Given
        when(articleRepository.findById(99L)).thenReturn(Optional.empty());

        // When/Then
        assertThatThrownBy(() -> articleService.deleteArticle(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Article not found with id : '99'");
    }
}
