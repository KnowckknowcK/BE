package com.knu.KnowcKKnowcK.controller.articleSummary;

import com.knu.KnowcKKnowcK.apiResponse.ApiResponseDto;
import com.knu.KnowcKKnowcK.apiResponse.SuccessCode;
import com.knu.KnowcKKnowcK.dto.requestdto.SummaryRequestDto;
import com.knu.KnowcKKnowcK.dto.responsedto.SummaryHistoryResponseDto;
import com.knu.KnowcKKnowcK.dto.responsedto.SummaryResponseDto;
import com.knu.KnowcKKnowcK.service.articleSummary.LoadSummaryService;
import com.knu.KnowcKKnowcK.service.articleSummary.SaveSummaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/summary", produces = "application/json;charset=UTF-8")
@Tag(name = "요약 관련 API", description = "요약 관련 API")
public class SummaryController {

    @Autowired
    SaveSummaryService saveSummaryService;

    @Autowired
    LoadSummaryService loadSummaryService;

    @GetMapping("/load")
    @Operation(summary = "요약 진행 내역 로드", description = "요약 작성 시작 시 히스토리가 있는 경우 이전 소요 시간과 요약 내용 로딩")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "요약 히스토리 가져오기 성공"),
            @ApiResponse(responseCode = "400", description = "요약 히스토리 가져오기 실패")})
    ApiResponseDto<SummaryHistoryResponseDto> loadSummaryHistory(@RequestParam @Valid long userId, @RequestParam @Valid int articleId) {

        SummaryHistoryResponseDto responseDto = loadSummaryService.loadSummaryHistory(userId, articleId);
        return ApiResponseDto.success(SuccessCode.OK, responseDto);
    }

    @PostMapping("/save")
    @Operation(summary = "진행 중인 요약 저장", description = "지문을 읽고 작성한 요약을 저장")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "요약 저장 성공"),
            @ApiResponse(responseCode = "400", description = "요약 히스토리 가져오기 실패")})
    ApiResponseDto<?> saveSummary(@RequestBody @Valid SummaryRequestDto dto){

            SummaryResponseDto summaryResponseDto = saveSummaryService.saveSummary(dto);
            return ApiResponseDto.success(SuccessCode.OK, summaryResponseDto);
    }

    @PostMapping("/submit")
    @Operation(summary = "요약 피드백 받기", description = "작성한 요약에 대한 AI  피드백에 제공됨")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "요약 피드백 반환 성공"),
            @ApiResponse(responseCode = "400", description = "요약 피드백 반환 실패")})
    ApiResponseDto<?> getSummaryFeedback(@RequestBody @Valid SummaryRequestDto dto){

        SummaryResponseDto summaryResponseDto = saveSummaryService.getSummaryFeedback(dto);
        return ApiResponseDto.success(SuccessCode.OK, summaryResponseDto);
    }
}
