package com.knu.KnowcKKnowcK.exception;

import lombok.Getter;

@Getter
public class ExceptionDto {
    private int status;
    private ErrorCode error;
    private String message;

    public ExceptionDto(ErrorCode error) {
        this.status = error.getStatus();
        this.error = error;
        this.message = error.getMessage();
    }

    public ExceptionDto(ErrorCode error, String message) {
        this.status = error.getStatus();
        this.error = error;
        this.message = message;
    }

}
