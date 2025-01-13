package com.blog.mapper;

import com.blog.dto.CommentDto;
import com.blog.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "article",source="article",ignore = true )
    CommentDto toDto(Comment comment);
    Comment toEntity(CommentDto commentDto);
    List<CommentDto> toDto(List<Comment> comment);
    List<Comment> toEntity(List<CommentDto> commentDto);
}
