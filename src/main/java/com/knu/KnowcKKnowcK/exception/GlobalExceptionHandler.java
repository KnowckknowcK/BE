package com.knu.KnowcKKnowcK.exception;
import com.knu.KnowcKKnowcK.apiResponse.ApiResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> ahiExceptionHandler(CustomException exception) {
        exception.printStackTrace();

        return ResponseEntity.status(exception.getErrorCode().getError())
                .body(new ExceptionDto(exception.getErrorCode()));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public @ResponseBody ApiResponse<ExceptionDto> validExceptionHandler(MethodArgumentNotValidException exception) {
        exception.printStackTrace();

        return ApiResponse.error(ErrorCode.INVALID_INPUT, new ExceptionDto(ErrorCode.INVALID_INPUT, "잘못된 입력입니다."));
    }
}
