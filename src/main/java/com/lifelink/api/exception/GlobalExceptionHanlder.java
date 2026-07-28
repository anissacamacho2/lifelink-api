package com.lifelink.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHanlder {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleAuthException(ApiException ex) {
        ErrorResponse errorRespone = new ErrorResponse(ex.getCode(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorRespone);
    }
    // @ExceptionHandler(Exception.class)
    // public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
    //     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    //         .body(new ErrorResponse(
    //                 ex.getCode,
    //                 "An unexpected error occurred."
    //         ));
    // }
}
