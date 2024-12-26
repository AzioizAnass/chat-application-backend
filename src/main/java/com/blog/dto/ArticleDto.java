package com.blog.dto;

import lombok.Data;
import java.util.List;

@Data
public class ArticleDto {
    private Long articleId;
    private String content;
    private String creationDate;
    private String title;
    private List<CommentDto> comments;
}
