package com.blog.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setTitle("Resource not found");
        return new ResponseEntity<>(problemDetail, HttpStatusCode.valueOf(problemDetail.getStatus()));
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<Object> handleBadRequest(BadRequestException ex) {
        ApiError error = new ApiError(
            HttpStatus.BAD_REQUEST,
            "Invalid request",
            ex.getMessage()
        );
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String details = ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage();
        ApiError error = new ApiError(
            HttpStatus.CONFLICT,
            "Database constraint violation",
            details
        );
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<Object> handleBadCredentials(BadCredentialsException ex) {
        ApiError error = new ApiError(
            HttpStatus.UNAUTHORIZED,
            "Authentication failed",
            "Invalid username or password"
        );
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDenied(AccessDeniedException ex) {
        ApiError error = new ApiError(
            HttpStatus.FORBIDDEN,
            "Access denied",
            "You don't have permission to perform this action"
        );
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllExceptions(Exception ex) {
        log.error("Unexpected error occurred", ex);
        ApiError error = new ApiError(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Internal server error",
            "An unexpected error occurred. Please try again later."
        );
        return new ResponseEntity<>(error, error.getStatus());
    }
}
