package com.springBootTesting.springBootTesting.advices;

import com.springBootTesting.springBootTesting.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalRunTimeException {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?>handleNotFoundException(ResourceNotFoundException ex){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?>handleNotFoundException(RuntimeException ex){
        return ResponseEntity.internalServerError().build();
    }
}
