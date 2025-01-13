package com.blog.mapper;

import com.blog.dto.ArticleDto;
import com.blog.entity.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses={CommentMapper.class})
public interface ArticleMapper {
    ArticleDto toDto(Article article);
    Article toEntity(ArticleDto articleDto);
}
