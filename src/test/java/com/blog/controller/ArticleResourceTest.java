package com.blog.controller;

import com.blog.dto.ArticleDto;
import com.blog.entity.Article;
import com.blog.exception.ResourceNotFoundException;
import com.blog.mapper.ArticleMapper;
import com.blog.service.ArticleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArticleResource.class)
class ArticleResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    @MockBean
    private ArticleMapper articleMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private Article article;
    private ArticleDto articleDto;

    @BeforeEach
    void setUp() {
        article = new Article();
        article.setArticleId(1L);
        article.setTitle("Test Article");
        article.setContent("Test Content");
        article.setCreationDate("2024-12-09T21:03:44Z");

        articleDto = new ArticleDto();
        articleDto.setArticleId(1L);
        articleDto.setTitle("Test Article");
        articleDto.setContent("Test Content");
        articleDto.setCreationDate("2024-12-09T21:03:44Z");
    }

    @Test
    void getAllArticles_ShouldReturnListOf() throws Exception {
        when(articleService.getAllArticles()).thenReturn(Arrays.asList(article));
        when(articleMapper.toDto(any(Article.class))).thenReturn(articleDto);

        mockMvc.perform(get("/api/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Test Article"))
                .andExpect(jsonPath("$[0].content").value("Test Content"));
    }

    @Test
    void getArticleById_WithValidId_ShouldReturnArticle() throws Exception {
        when(articleService.getById(1L)).thenReturn(Optional.of(article));
        when(articleMapper.toDto(article)).thenReturn(articleDto);

        mockMvc.perform(get("/api/articles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Test Article"))
                .andExpect(jsonPath("$.content").value("Test Content"));
    }

    @Test
    void getArticleById_WithInvalidId_ShouldReturn404() throws Exception {
        when(articleService.getById(99L))
                .thenThrow(new ResourceNotFoundException("Article", "id", 99L));

        mockMvc.perform(get("/api/articles/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createArticle_ShouldReturnCreatedArticle() throws Exception {
        when(articleMapper.toEntity(any(ArticleDto.class))).thenReturn(article);
        when(articleService.createArticle(any(Article.class))).thenReturn(article);
        when(articleMapper.toDto(any(Article.class))).thenReturn(articleDto);

        mockMvc.perform(post("/api/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(articleDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Article"))
                .andExpect(jsonPath("$.content").value("Test Content"));
    }

    @Test
    void updateArticle_WithValidId_ShouldReturnUpdatedArticle() throws Exception {
        when(articleMapper.toEntity(any(ArticleDto.class))).thenReturn(article);
        when(articleService.updateArticle(eq(1L), any(Article.class))).thenReturn(article);
        when(articleMapper.toDto(any(Article.class))).thenReturn(articleDto);

        mockMvc.perform(put("/api/articles/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(articleDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Article"))
                .andExpect(jsonPath("$.content").value("Test Content"));
    }

    @Test
    void updateArticle_WithInvalidId_ShouldReturn404() throws Exception {
        when(articleMapper.toEntity(any(ArticleDto.class))).thenReturn(article);
        when(articleService.updateArticle(eq(99L), any(Article.class)))
                .thenThrow(new ResourceNotFoundException("Article", "id", 99L));

        mockMvc.perform(put("/api/articles/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(articleDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteArticle_WithValidId_ShouldReturn200() throws Exception {
        mockMvc.perform(delete("/api/articles/1"))
                .andExpect(status().isOk());
    }
}
