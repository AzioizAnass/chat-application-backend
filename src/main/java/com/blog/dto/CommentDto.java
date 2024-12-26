package com.blog.dto;

import lombok.Data;

@Data
public class CommentDto {
    private Long id;
    private String content;
    private String date;
    private Long articleId;
    private Long userId;
}
