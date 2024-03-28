package com.knu.KnowcKKnowcK.controller;

import com.knu.KnowcKKnowcK.apiResponse.ApiResponse;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import com.knu.KnowcKKnowcK.apiResponse.ExampleDto;
import com.knu.KnowcKKnowcK.apiResponse.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
@Tag(name="Example for swagger and response format",description="")
public class ExampleController {
    @GetMapping("/example")
    @Operation(summary="예시 API",description="swagger와 apiResponseDto의 사용방법에 대한 예시를 보여주기 위한 api")
    @Parameters({@Parameter(name = "op", description = "성공/실패", example = "true")})
    public ApiResponse<ExampleDto> example(@RequestParam(name="op")String op){
        if(op.equals("true"))
            return ApiResponse.success(SuccessCode.OK, new ExampleDto("성공한 요청입니다.", LocalDate.now()));
        else if (op.equals("false"))
            return ApiResponse.error(ErrorCode.FAILED, new ExampleDto("실패한 요청입니다.",LocalDate.now()));
        else
            throw new CustomException(ErrorCode.FAILED);
    }
}
