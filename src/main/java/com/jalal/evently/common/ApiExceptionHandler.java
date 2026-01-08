package com.jalal.evently.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    public record ApiError(String code, String message) {}

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiError> handleApi(ApiException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(new ApiError(ex.getCode(), ex.getMessage()));
    }

    // Fallback: unexpected runtime errors become 500 with generic message
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntime(RuntimeException ex) {
        return ResponseEntity
                .status(500)
                .body(new ApiError("INTERNAL_ERROR", "Something went wrong"));
    }
}
