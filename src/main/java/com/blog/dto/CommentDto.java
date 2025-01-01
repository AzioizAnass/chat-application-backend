package com.blog.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CommentDto {
    private Long id;
    private String content;
    private String date;
    private ArticleDto article;
    private UserDto user;
}
