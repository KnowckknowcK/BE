package com.knu.KnowcKKnowcK.controller;

import com.knu.KnowcKKnowcK.apiResponse.ApiResponseDto;
import com.knu.KnowcKKnowcK.apiResponse.SuccessCode;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import com.knu.KnowcKKnowcK.service.BatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BatchController {
    private final BatchService batchService;
    @PostMapping("/batch")
    public ApiResponseDto<String> makeDebateRoomFromArticle() throws Exception {
        if (batchService.runMakeDebateRoomBatch()){
            return ApiResponseDto.success(SuccessCode.OK, "batch run successful");
        }
        else throw new CustomException(ErrorCode.FAILED_BATCH);
    }
}
