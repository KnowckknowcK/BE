package com.knu.KnowcKKnowcK.exception;

import lombok.Getter;

@Getter
public class CustomException {
    private int result;
    private ErrorCode errorCode;
    private String message;

    public CustomException(ErrorCode errorCode) {
        this.result = errorCode.getStatus();
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }
}
