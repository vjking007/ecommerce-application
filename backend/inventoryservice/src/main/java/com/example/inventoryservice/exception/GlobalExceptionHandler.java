package com.example.inventoryservice.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleProductNotFound(ProductNotFoundException ex, HttpServletRequest request) {
        return new ResponseEntity<>(
                buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ApiErrorResponse> handleInsufficientStock(InsufficientStockException ex, HttpServletRequest request) {
        return new ResponseEntity<>(
                buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {
        return new ResponseEntity<>(
                buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request.getRequestURI()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    private ApiErrorResponse buildErrorResponse(HttpStatus status, String message, String path) {
        return ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(path)
                .build();
    }
}

