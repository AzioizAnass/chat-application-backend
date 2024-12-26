package com.blog.service;

import com.blog.dto.ArticleDto;
import com.blog.entity.Article;
import com.blog.exception.ResourceNotFoundException;
import com.blog.mapper.ArticleMapper;
import com.blog.repository.ArticleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    public List<ArticleDto> getAllArticles() {
        return articleRepository.findAll().stream()
                .map(articleMapper::toDto)
                .collect(Collectors.toList());
    }

    public ArticleDto getById(Long id){
        Article article= articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", id));
         return articleMapper.toDto(article);
    }

    public ArticleDto createArticle(ArticleDto articleDto) {
        Article article = articleMapper.toEntity(articleDto);
        article.setCreationDate(Instant.now().toString());
        return articleMapper.toDto(articleRepository.save(article));
    }

    @Transactional
    public Article updateArticle(Long id, Article articleDetails) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", id));
        
        article.setTitle(articleDetails.getTitle());
        article.setContent(articleDetails.getContent());
        return article;
    }

    public void deleteArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", id));
        articleRepository.delete(article);
    }
}
