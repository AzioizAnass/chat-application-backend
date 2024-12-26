package com.blog.mapper;

import com.blog.dto.CommentDto;
import com.blog.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(source = "article.id", target = "articleId")
    @Mapping(source = "user.id", target = "userId")
    CommentDto toDto(Comment comment);

    @Mapping(target = "article", ignore = true)
    @Mapping(target = "user", ignore = true)
    Comment toEntity(CommentDto commentDto);
}
