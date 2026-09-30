package com.poonam.notestodoapp.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // ===========================
    // Task Not Found
    // ===========================

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTaskNotFound(TaskNotFoundException exception) {

ErrorResponse errorResponse = new ErrorResponse(
        HttpStatus.NOT_FOUND.value(),
        exception.getMessage(),
        LocalDateTime.now()
);
return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // ===================================================
    // Validation Errors (@NotBlank, @Size, @NotNull waghera)
    // ===================================================

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public  ResponseEntity<Map<String,String>> handleValidationErrors(
            MethodArgumentNotValidException exception){
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error->errors.put(error.getField(),error.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    // ===========================
    // Task Already completed
    // ===========================

    @ExceptionHandler(TaskAlreadyCompletedException.class)
    public ResponseEntity<ErrorResponse> handletaskAlreadyCompleted(TaskAlreadyCompletedException exception){
        ErrorResponse errorResponse = new ErrorResponse(
        HttpStatus.CONFLICT.value(),
                exception.getMessage(),
                LocalDateTime.now()
);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }



}
