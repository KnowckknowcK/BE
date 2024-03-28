package com.knu.KnowcKKnowcK.apiResponse;

import com.knu.KnowcKKnowcK.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class ApiResponse<T> {

    private final int code;
    private final String message;
    private T data;

    public static <T> ApiResponse<T> success(SuccessCode successCode, T data) {
        return new ApiResponse<>(successCode.getSuccess().value(),
                successCode.getMessage(), data);
    }
    public static <T> ApiResponse<T> error(ErrorCode errorCode, T data) {
        return new ApiResponse<>(errorCode.getError().value(),
                errorCode.getMessage(), data);
    }
}
