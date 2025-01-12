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
    date = "2025-01-02T21:07:06+0000",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.v20241217-1506, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class ArticleMapperImpl implements ArticleMapper {

    @Override
    public ArticleDto toDto(Article article) {
        if ( article == null ) {
            return null;
        }

        ArticleDto articleDto = new ArticleDto();

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

        article.setComments( commentDtoListToCommentList( articleDto.getComments() ) );
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
        user.setId( userDto.getId() );
        user.setUsername( userDto.getUsername() );

        return user;
    }

    protected Comment commentDtoToComment(CommentDto commentDto) {
        if ( commentDto == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setArticle( toEntity( commentDto.getArticle() ) );
        comment.setContent( commentDto.getContent() );
        comment.setDate( commentDto.getDate() );
        comment.setId( commentDto.getId() );
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
