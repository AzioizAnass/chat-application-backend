package com.blog.mapper;

import com.blog.dto.ArticleDto;
import com.blog.dto.CommentDto;
import com.blog.entity.Article;
import com.blog.entity.Comment;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-24T20:07:02+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
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

        articleDto.setContent( article.getContent() );
        articleDto.setCreationDate( article.getCreationDate() );
        articleDto.setTitle( article.getTitle() );

        return articleDto;
    }

    @Override
    public Article toEntity(ArticleDto articleDto) {
        if ( articleDto == null ) {
            return null;
        }

        Article article = new Article();

        article.setContent( articleDto.getContent() );
        article.setCreationDate( articleDto.getCreationDate() );
        article.setTitle( articleDto.getTitle() );
        article.setComments( commentDtoListToCommentList( articleDto.getComments() ) );

        return article;
    }

    protected List<Comment> commentDtoListToCommentList(List<CommentDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Comment> list1 = new ArrayList<Comment>( list.size() );
        for ( CommentDto commentDto : list ) {
            list1.add( commentMapper.toEntity( commentDto ) );
        }

        return list1;
    }
}
