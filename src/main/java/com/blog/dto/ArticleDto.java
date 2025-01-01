package com.blog.dto;

import lombok.Data;
import java.util.List;

@Data
public class ArticleDto {
    private Long id;
    private String content;
    private String creationDate;
    private String title;
    private List<CommentDto> comments;
}
