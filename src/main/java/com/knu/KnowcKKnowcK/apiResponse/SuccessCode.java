package com.knu.KnowcKKnowcK.apiResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    /**
     * 대문자로 SuccessCode명 정의 (상태코드 / HttpStatus / 메세지)
     */
    OK(200,HttpStatus.OK, "OK"),
    CREATED_SUCCESS(201,HttpStatus.CREATED, "저장에 성공했습니다");

    private int status;
    private HttpStatus success;
    private String message;
}
