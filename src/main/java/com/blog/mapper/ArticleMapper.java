package com.blog.mapper;

import com.blog.dto.ArticleDto;
import com.blog.entity.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CommentMapper.class})
public interface ArticleMapper {

    @Mapping(target = "comments", ignore = true)
    ArticleDto toDto(Article article);
    Article toEntity(ArticleDto articleDto);
}
