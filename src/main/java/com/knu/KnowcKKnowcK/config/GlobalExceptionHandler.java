package com.knu.KnowcKKnowcK.config;

import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ExceptionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> CustomExceptionHandler(CustomException e) {
        e.printStackTrace();

        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(new ExceptionDto(e.getErrorCode()));
    }
}
