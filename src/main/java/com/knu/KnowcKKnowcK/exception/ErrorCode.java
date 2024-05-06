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
    INVALID_PERMISSION(401,HttpStatus.UNAUTHORIZED, "권한이 없습니다"),
    INVALID_INPUT(400, HttpStatus.BAD_REQUEST,"잘못된 요청입니다."),
    ALREADY_REGISTERED(409, HttpStatus.CONFLICT,"이미 가입된 회원입니다"),
    FAILED_UPLOAD(500, HttpStatus.INTERNAL_SERVER_ERROR,"이미지 업로드에 실패했습니다."),
    FEEDBACK_NOT_EXIST(400, HttpStatus.BAD_REQUEST,"피드백을 받지 않은 요약입니다.");

    private int status;
    private HttpStatus error;
    private String message;
}
