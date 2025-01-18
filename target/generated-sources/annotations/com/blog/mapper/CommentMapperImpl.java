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
    date = "2025-01-17T23:57:57+0000",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.v20241217-1506, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public CommentDto toDto(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentDto commentDto = new CommentDto();

        commentDto.setContent( comment.getContent() );
        commentDto.setDate( comment.getDate() );
        commentDto.setId( comment.getId() );
        commentDto.setUser( userToUserDto( comment.getUser() ) );

        return commentDto;
    }

    @Override
    public Comment toEntity(CommentDto commentDto) {
        if ( commentDto == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setArticle( articleDtoToArticle( commentDto.getArticle() ) );
        comment.setContent( commentDto.getContent() );
        comment.setDate( commentDto.getDate() );
        comment.setId( commentDto.getId() );
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

        String email = null;
        String firstName = null;
        Long id = null;
        String lastName = null;
        String role = null;
        String username = null;

        email = user.getEmail();
        firstName = user.getFirstName();
        id = user.getId();
        lastName = user.getLastName();
        role = user.getRole();
        username = user.getUsername();

        UserDto userDto = new UserDto( id, email, username, firstName, lastName, role );

        return userDto;
    }

    protected Article articleDtoToArticle(ArticleDto articleDto) {
        if ( articleDto == null ) {
            return null;
        }

        Article article = new Article();

        article.setComments( toEntity( articleDto.getComments() ) );
        article.setContent( articleDto.getContent() );
        article.setCreationDate( articleDto.getCreationDate() );
        article.setId( articleDto.getId() );
        article.setTitle( articleDto.getTitle() );

        return article;
    }

    protected User userDtoToUser(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setEmail( userDto.getEmail() );
        user.setFirstName( userDto.getFirstName() );
        user.setId( userDto.getId() );
        user.setLastName( userDto.getLastName() );
        user.setRole( userDto.getRole() );
        user.setUsername( userDto.getUsername() );

        return user;
    }
}
