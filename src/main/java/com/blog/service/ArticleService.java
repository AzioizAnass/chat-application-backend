package com.blog.service;

import com.blog.dto.ArticleDto;
import com.blog.entity.Article;
import com.blog.exception.ResourceNotFoundException;
import com.blog.mapper.ArticleMapper;
import com.blog.repository.ArticleRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public List<ArticleDto> searchByKeywordArticle(String keyword) {
        return articleRepository.searchByKeyword(keyword)
                .stream()
                .map(article -> articleMapper.toDto(article))
                .collect(Collectors.toList());
    }

    public ArticleDto getById(Long id){
        Article article= articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", id));
         return articleMapper.toDto(article);
    }

    public Page<ArticleDto> findArticlesByPage(Integer pageNo , Integer pageSize) {
        Pageable pagebale = PageRequest.of(pageNo, pageSize);
        Page<Article> articlePage = articleRepository.getAllBy(pagebale);
        return articlePage.map(articleMapper::toDto);
    }
    public ArticleDto createArticle(ArticleDto articleDto) {
        Article article = articleMapper.toEntity(articleDto);
        article.setCreationDate(Instant.now().toString());
        return articleMapper.toDto(articleRepository.save(article));
    }

    @Transactional
    public ArticleDto updateArticle(Long id, ArticleDto articleDetails) {
        Article article = articleMapper.toEntity(articleDetails);
        article.setId(id);
        articleRepository.save(article);
        return articleMapper.toDto(article);
    }

    public void deleteArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", id));
        articleRepository.delete(article);
    }
}
