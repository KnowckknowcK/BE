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

    FAILED(500,HttpStatus.BAD_REQUEST,"ChatGpt로부터 적절한 응답을 받아오지 못했습니다."),
    USER_NOT_FOUND(404,HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다"),
    INVALID_PERMISSION(401,HttpStatus.UNAUTHORIZED, "권한이 없습니다"),
    INVALID_INPUT(400, HttpStatus.BAD_REQUEST,"잘못된 요청입니다."),
    ALREADY_REGISTERED(409, HttpStatus.CONFLICT,"이미 가입된 회원입니다."),
    ALREADY_EXISTED(409, HttpStatus.CONTINUE, "이미 피드백 받은 내용이 존재합니다."),
    OAUTH_MEMBER(409, HttpStatus.CONFLICT,"소셜 로그인 가입 회원입니다."),
    FAILED_UPLOAD(500, HttpStatus.INTERNAL_SERVER_ERROR,"이미지 업로드에 실패했습니다."),
    FAILED_BATCH(500, HttpStatus.INTERNAL_SERVER_ERROR, "배치 작업에 실패했습니다."),
    FEEDBACK_NOT_EXIST(400, HttpStatus.BAD_REQUEST,"피드백을 받지 않은 요약입니다."),
    TOKEN_INVALID(401, HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰: 로그인이 필요합니다."),
    TOKEN_EXPIRED(401, HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    UNAUTHORIZED(403, HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    OAUTH_LOGIN_FAIL(400, HttpStatus.BAD_REQUEST,"소셜 로그인에 실패하였습니다."),
    WRONG_CODE(400, HttpStatus.BAD_REQUEST, "인증코드가 틀렸습니다."),
    FAILED_SIGNUP(400, HttpStatus.BAD_REQUEST, "회원가입에 실패하였습니다."),
    FAILED_SEND_CODE(500, HttpStatus.INTERNAL_SERVER_ERROR, "인증코드 발송에 실패하였습니다.");

    private int status;
    private HttpStatus error;
    private String message;
}
