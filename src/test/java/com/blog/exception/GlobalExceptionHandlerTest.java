package com.blog.exception;

import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void handleResourceNotFound_ShouldReturnNotFoundStatus() {
        // Given
        ResourceNotFoundException ex = new ResourceNotFoundException("Article", "id", 1L);

        // When
        ResponseEntity<Object> response = exceptionHandler.handleResourceNotFound(ex);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isInstanceOf(ApiError.class);
        ApiError error = (ApiError) response.getBody();
        assertThat(error.getMessage()).contains("Resource not found");
        assertThat(error.getDetails()).contains("Article not found with id : '1'");
    }

    @Test
    void handleBadRequest_ShouldReturnBadRequestStatus() {
        // Given
        BadRequestException ex = new BadRequestException("Invalid data");

        // When
        ResponseEntity<Object> response = exceptionHandler.handleBadRequest(ex);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isInstanceOf(ApiError.class);
        ApiError error = (ApiError) response.getBody();
        assertThat(error.getMessage()).isEqualTo("Invalid request");
        assertThat(error.getDetails()).isEqualTo("Invalid data");
    }

    @Test
    void handleDataIntegrityViolation_ShouldReturnConflictStatus() {
        // Given
        String errorMessage = "Duplicate entry";
        DataIntegrityViolationException ex = new DataIntegrityViolationException(errorMessage);

        // When
        ResponseEntity<Object> response = exceptionHandler.handleDataIntegrityViolation(ex);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody()).isInstanceOf(ApiError.class);
        ApiError error = (ApiError) response.getBody();
        assertThat(error.getMessage()).isEqualTo("Database constraint violation");
        assertThat(error.getDetails()).isEqualTo(errorMessage);
    }

    @Test
    void handleAllExceptions_ShouldReturnInternalServerError() {
        // Given
        Exception ex = new RuntimeException("Unexpected error");

        // When
        ResponseEntity<Object> response = exceptionHandler.handleAllExceptions(ex);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isInstanceOf(ApiError.class);
        ApiError error = (ApiError) response.getBody();
        assertThat(error.getMessage()).isEqualTo("Internal server error");
        assertThat(error.getDetails()).isEqualTo("An unexpected error occurred. Please try again later.");
    }
}
