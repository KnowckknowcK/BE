package com.knu.KnowcKKnowcK.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExceptionDto {
    private int code;
    private String message;
    private HttpStatus data;

    public ExceptionDto(ErrorCode error) {
        this.code = error.getStatus();
        this.message = error.getMessage();
        this.data = error.getError();
    }

    public ExceptionDto(ErrorCode error, String message) {
        this.code = error.getStatus();
        this.message = message;
        this.data = error.getError();
    }

}
