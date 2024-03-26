package com.example.demo.exception;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException ex, HttpStatus status){
        ApiError apiError = ApiError
                .builder()
                .message(ex.getMessage())
                .code(status.value())
                .Value(status.getReasonPhrase())
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler({ResourceExistsException.class, BadRequestException.class})
    public ResponseEntity<ApiError> handleResourceExistsException(ResourceExistsException ex, HttpStatus status){
        ApiError apiError = ApiError
                .builder()
                .message(ex.getMessage())
                .code(status.value())
                .Value(status.getReasonPhrase())
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
}
