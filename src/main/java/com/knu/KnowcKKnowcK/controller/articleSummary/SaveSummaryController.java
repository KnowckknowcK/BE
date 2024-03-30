package com.knu.KnowcKKnowcK.controller.articleSummary;

import com.knu.KnowcKKnowcK.apiResponse.ApiResponse;
import com.knu.KnowcKKnowcK.apiResponse.SuccessCode;
import com.knu.KnowcKKnowcK.dto.requestdto.SummaryRequestDto;
import com.knu.KnowcKKnowcK.dto.responsedto.SummaryResponseDto;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import com.knu.KnowcKKnowcK.exception.ExceptionDto;
import com.knu.KnowcKKnowcK.service.articleSummary.SaveSummaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/summary", produces = "application/json;charset=UTF-8")
public class SaveSummaryController {

    private final SaveSummaryService saveSummaryService;
    
    @PostMapping("/save")
    @Tag(name = "요약 저장", description = "지문을 읽고 작성한 요약을 저장하는 기능이다. 자동/수동으로 저장할 수 있다.")
    @Operation(summary = "요약 저장", description = "지문을 읽고 작성한 요약을 저장하는 기능이다. 자동/수동으로 저장할 수 있다.")
    ApiResponse<?> saveSummary(@RequestBody @Valid SummaryRequestDto dto){

        try{
            SummaryResponseDto summaryResponseDto = saveSummaryService.saveSummary(dto);
            return ApiResponse.success(SuccessCode.OK, summaryResponseDto);

        }catch (CustomException e) {
            return ApiResponse.error(ErrorCode.FAILED, new ExceptionDto(ErrorCode.FAILED,"잘못된 요청입니다."));
        }

    }
}
