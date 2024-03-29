package com.knu.KnowcKKnowcK.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /**
     * 대문자로 ErrorCode명 정의 (상태코드 / HttpStatus / 메세지)
     */

    // 이건 그냥 예시용
    FAILED(500,HttpStatus.BAD_REQUEST,"FAILED"),
    USER_NOT_FOUND(404,HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다"),
    INVALID_PERMISSION(401,HttpStatus.UNAUTHORIZED, "권한이 없습니다");
    private int status;
    private HttpStatus error;
    private String message;
}