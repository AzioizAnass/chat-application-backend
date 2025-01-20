package com.blog.mapper;

import com.blog.dto.ArticleDto;
import com.blog.entity.Article;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-20T00:04:28+0000",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
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

        articleDto.setId( article.getId() );
        articleDto.setContent( article.getContent() );
        articleDto.setCreationDate( article.getCreationDate() );
        articleDto.setTitle( article.getTitle() );
        articleDto.setComments( commentMapper.toDto( article.getComments() ) );

        return articleDto;
    }

    @Override
    public Article toEntity(ArticleDto articleDto) {
        if ( articleDto == null ) {
            return null;
        }

        Article article = new Article();

        article.setId( articleDto.getId() );
        article.setContent( articleDto.getContent() );
        article.setCreationDate( articleDto.getCreationDate() );
        article.setTitle( articleDto.getTitle() );
        article.setComments( commentMapper.toEntity( articleDto.getComments() ) );

        return article;
    }
}
