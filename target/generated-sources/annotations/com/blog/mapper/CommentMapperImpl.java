package com.blog.mapper;

import com.blog.dto.CommentDto;
import com.blog.entity.Article;
import com.blog.entity.Comment;
import com.blog.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-24T20:19:20+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public CommentDto toDto(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentDto commentDto = new CommentDto();

        commentDto.setArticleId( commentArticleId( comment ) );
        commentDto.setUserId( commentUserId( comment ) );
        commentDto.setId( comment.getId() );
        commentDto.setContent( comment.getContent() );
        commentDto.setDate( comment.getDate() );

        return commentDto;
    }

    @Override
    public Comment toEntity(CommentDto commentDto) {
        if ( commentDto == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setId( commentDto.getId() );
        comment.setContent( commentDto.getContent() );
        comment.setDate( commentDto.getDate() );

        return comment;
    }

    private Long commentArticleId(Comment comment) {
        if ( comment == null ) {
            return null;
        }
        Article article = comment.getArticle();
        if ( article == null ) {
            return null;
        }
        Long id = article.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long commentUserId(Comment comment) {
        if ( comment == null ) {
            return null;
        }
        User user = comment.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
