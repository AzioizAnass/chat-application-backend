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
    date = "2025-01-13T20:14:15+0000",
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

        commentDto.setId( comment.getId() );
        commentDto.setContent( comment.getContent() );
        commentDto.setDate( comment.getDate() );
        commentDto.setUser( userToUserDto( comment.getUser() ) );

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
        comment.setArticle( articleDtoToArticle( commentDto.getArticle() ) );
        comment.setUser( userDtoToUser( commentDto.getUser() ) );

        return comment;
    }

    @Override
    public List<CommentDto> toDto(List<Comment> comment) {
        if ( comment == null ) {
            return null;
        }

        List<CommentDto> list = new ArrayList<CommentDto>( comment.size() );
        for ( Comment comment1 : comment ) {
            list.add( toDto( comment1 ) );
        }

        return list;
    }

    @Override
    public List<Comment> toEntity(List<CommentDto> commentDto) {
        if ( commentDto == null ) {
            return null;
        }

        List<Comment> list = new ArrayList<Comment>( commentDto.size() );
        for ( CommentDto commentDto1 : commentDto ) {
            list.add( toEntity( commentDto1 ) );
        }

        return list;
    }

    protected UserDto userToUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId( user.getId() );
        userDto.setEmail( user.getEmail() );
        userDto.setUsername( user.getUsername() );
        userDto.setFirstName( user.getFirstName() );
        userDto.setLastName( user.getLastName() );

        return userDto;
    }

    protected Article articleDtoToArticle(ArticleDto articleDto) {
        if ( articleDto == null ) {
            return null;
        }

        Article article = new Article();

        article.setId( articleDto.getId() );
        article.setContent( articleDto.getContent() );
        article.setCreationDate( articleDto.getCreationDate() );
        article.setTitle( articleDto.getTitle() );
        article.setComments( toEntity( articleDto.getComments() ) );

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
        user.setFirstName( userDto.getFirstName() );
        user.setLastName( userDto.getLastName() );

        return user;
    }
}
