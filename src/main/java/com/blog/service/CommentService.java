package com.blog.service;

import com.blog.dto.CommentDto;
import com.blog.entity.Comment;
import com.blog.exception.ResourceNotFoundException;
import com.blog.mapper.ArticleMapper;
import com.blog.mapper.CommentMapper;
import com.blog.mapper.UserMapper;
import com.blog.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public List<CommentDto> getAllComments() {
        return commentMapper.toDto(commentRepository.findAll());
    }

    public CommentDto getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("comment", "id", id));

        return commentMapper.toDto(comment);
    }

    public CommentDto createComment(CommentDto commentDto) {
        Comment comment = commentMapper.toEntity(commentDto);
        return commentMapper.toDto(commentRepository.save(comment));
    }

    @Transactional
    public CommentDto updateComment(Long id, CommentDto commentDetails) {
        Comment comment = commentMapper.toEntity(commentDetails);
        comment.setId(id);
        return commentMapper.toDto(commentRepository.save(comment));

    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
