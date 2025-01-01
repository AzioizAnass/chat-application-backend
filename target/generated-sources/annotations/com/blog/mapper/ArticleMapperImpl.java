package com.blog.mapper;

import com.blog.dto.ArticleDto;
import com.blog.dto.CommentDto;
import com.blog.dto.UserDto;
import com.blog.entity.Article;
import com.blog.entity.Comment;
import com.blog.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-31T18:54:07+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class ArticleMapperImpl implements ArticleMapper {

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
        article.setComments( commentDtoListToCommentList( articleDto.getComments() ) );

        return article;
    }

    protected User userDtoToUser(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setId( userDto.getId() );
        user.setEmail( userDto.getEmail() );
        user.setUsername( userDto.getUsername() );

        return user;
    }

    protected Comment commentDtoToComment(CommentDto commentDto) {
        if ( commentDto == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setId( commentDto.getId() );
        comment.setContent( commentDto.getContent() );
        comment.setDate( commentDto.getDate() );
        comment.setArticle( toEntity( commentDto.getArticle() ) );
        comment.setUser( userDtoToUser( commentDto.getUser() ) );

        return comment;
    }

    protected List<Comment> commentDtoListToCommentList(List<CommentDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Comment> list1 = new ArrayList<Comment>( list.size() );
        for ( CommentDto commentDto : list ) {
            list1.add( commentDtoToComment( commentDto ) );
        }

        return list1;
    }
}
