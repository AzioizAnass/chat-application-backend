package com.blog.controller;

import com.blog.dto.CommentDto;
import com.blog.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CommentIntegrationTest {

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentResource commentResource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllComments_ShouldReturnListOfComments() {
        // Arrange
        List<CommentDto> mockComments = new ArrayList<>();
        CommentDto comment1 = new CommentDto();
        comment1.setId(1L);
        comment1.setContent("First comment");
        mockComments.add(comment1);

        CommentDto comment2 = new CommentDto();
        comment2.setId(2L);
        comment2.setContent("Second comment");
        mockComments.add(comment2);

        when(commentService.getAllComments()).thenReturn(mockComments);

        // Act
        List<CommentDto> result = commentResource.getAllComments();

        // Assert
        assertEquals(mockComments, result);
        verify(commentService, times(1)).getAllComments();
    }

    @Test
    void getCommentById_ShouldReturnComment() {
        // Arrange
        Long commentId = 1L;
        CommentDto mockComment = new CommentDto();
        mockComment.setId(commentId);
        mockComment.setContent("Mock comment");

        when(commentService.getCommentById(commentId)).thenReturn(mockComment);

        // Act
        ResponseEntity<CommentDto> response = commentResource.getCommentById(commentId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockComment, response.getBody());
        verify(commentService, times(1)).getCommentById(commentId);
    }

    @Test
    void createComment_ShouldReturnCreatedComment() {
        // Arrange
        CommentDto newComment = new CommentDto();
        newComment.setContent("New comment");

        CommentDto createdComment = new CommentDto();
        createdComment.setId(1L);
        createdComment.setContent("New comment");

        when(commentService.createComment(newComment)).thenReturn(createdComment);

        // Act
        CommentDto result = commentResource.createComment(newComment);

        // Assert
        assertEquals(createdComment, result);
        verify(commentService, times(1)).createComment(newComment);
    }

    @Test
    void updateComment_ShouldReturnUpdatedComment() {
        // Arrange
        Long commentId = 1L;
        CommentDto updatedComment = new CommentDto();
        updatedComment.setContent("Updated comment");

        CommentDto mockUpdatedComment = new CommentDto();
        mockUpdatedComment.setId(commentId);
        mockUpdatedComment.setContent("Updated comment");

        when(commentService.updateComment(commentId, updatedComment)).thenReturn(mockUpdatedComment);

        // Act
        ResponseEntity<CommentDto> response = commentResource.updateComment(commentId, updatedComment);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockUpdatedComment, response.getBody());
        verify(commentService, times(1)).updateComment(commentId, updatedComment);
    }

    @Test
    void deleteComment_ShouldReturnVoidResponse() {
        // Arrange
        Long commentId = 1L;

        // Act
        ResponseEntity<Void> response = commentResource.deleteComment(commentId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        verify(commentService, times(1)).deleteComment(commentId);
    }
}
