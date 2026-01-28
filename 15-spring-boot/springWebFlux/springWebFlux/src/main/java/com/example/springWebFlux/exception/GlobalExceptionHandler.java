package com.example.springWebFlux.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.springWebFlux.model.ErrorResponse;

import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleNotFound(ResourceNotFoundException ex) {

        ErrorResponse error = new ErrorResponse("NOT_FOUND", ex.getMessage());

        return Mono.just(
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(error)
        );
    }
}
