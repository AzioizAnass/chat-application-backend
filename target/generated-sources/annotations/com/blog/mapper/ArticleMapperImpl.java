package com.blog.mapper;

import com.blog.dto.ArticleDto;
import com.blog.entity.Article;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-17T23:57:57+0000",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.v20241217-1506, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class ArticleMapperImpl implements ArticleMapper {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public ArticleDto toDto(Article article) {
        if ( article == null ) {
            return null;
        }

        ArticleDto articleDto = new ArticleDto();

        articleDto.setComments( commentMapper.toDto( article.getComments() ) );
        articleDto.setContent( article.getContent() );
        articleDto.setCreationDate( article.getCreationDate() );
        articleDto.setId( article.getId() );
        articleDto.setTitle( article.getTitle() );

        return articleDto;
    }

    @Override
    public Article toEntity(ArticleDto articleDto) {
        if ( articleDto == null ) {
            return null;
        }

        Article article = new Article();

        article.setComments( commentMapper.toEntity( articleDto.getComments() ) );
        article.setContent( articleDto.getContent() );
        article.setCreationDate( articleDto.getCreationDate() );
        article.setId( articleDto.getId() );
        article.setTitle( articleDto.getTitle() );

        return article;
    }
}
