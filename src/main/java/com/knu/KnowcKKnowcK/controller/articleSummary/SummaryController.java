package com.knu.KnowcKKnowcK.controller.articleSummary;

import com.knu.KnowcKKnowcK.apiResponse.ApiResponseDto;
import com.knu.KnowcKKnowcK.apiResponse.SuccessCode;
import com.knu.KnowcKKnowcK.dto.requestdto.SummaryRequestDto;
import com.knu.KnowcKKnowcK.dto.responsedto.MySummaryResponseDto;
import com.knu.KnowcKKnowcK.dto.responsedto.SummaryHistoryResponseDto;
import com.knu.KnowcKKnowcK.dto.responsedto.SummaryResponseDto;
import com.knu.KnowcKKnowcK.enums.Status;
import com.knu.KnowcKKnowcK.service.myPage.MySummaryService;
import com.knu.KnowcKKnowcK.service.summary.SummaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/summary", produces = "application/json;charset=UTF-8")
@Tag(name = "요약 관련 API", description = "요약 관련 API")
public class SummaryController {

    private final SummaryService summaryService;
    private final MySummaryService mySummaryService;


    @GetMapping("/load")
    @Operation(summary = "요약 진행 내역 로드", description = "요약 작성 시작 시 히스토리가 있는 경우 이전 소요 시간과 요약 내용 로딩")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "요약 히스토리 가져오기 성공"),
            @ApiResponse(responseCode = "400", description = "요약 히스토리 가져오기 실패")
    })
    ApiResponseDto<SummaryHistoryResponseDto> loadSummaryHistory(Authentication authentication,
                                                                 @RequestParam @Valid int articleId) {
        SummaryHistoryResponseDto responseDto = summaryService.loadSummaryHistory(authentication.getName(), articleId);
        return ApiResponseDto.success(SuccessCode.OK, responseDto);
    }



    @PostMapping("/save")
    @Operation(summary = "진행 중인 요약 저장", description = "지문을 읽고 작성한 요약을 저장")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "요약 저장 성공"),
            @ApiResponse(responseCode = "400", description = "요약 히스토리 가져오기 실패")
    })
    ApiResponseDto<SummaryResponseDto> saveSummary(Authentication authentication, @RequestBody @Valid SummaryRequestDto dto){

            SummaryResponseDto summaryResponseDto = summaryService.saveSummary(dto, authentication.getName());
            return ApiResponseDto.success(SuccessCode.OK, summaryResponseDto);
    }



    @PostMapping("/submit")
    @Operation(summary = "요약 피드백 받기", description = "작성한 요약에 대한 AI  피드백에 제공됨")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "요약 피드백 반환 성공"),
            @ApiResponse(responseCode = "400", description = "요약 피드백 반환 실패")
    })
    ApiResponseDto<SummaryResponseDto> getSummaryFeedback(Authentication authentication, @RequestBody @Valid SummaryRequestDto dto){

        SummaryResponseDto summaryResponseDto = summaryService.getSummaryFeedback(dto, authentication.getName());
        return ApiResponseDto.success(SuccessCode.OK, summaryResponseDto);
    }

    @GetMapping("")
    @Operation(summary="사용자 요약",description="사용자가 작성했거나 작성한 요약 조회")
    @Parameters({
            @Parameter(name = "status",description = "원하는 요약이 작성 중인지,작성 한건지",example = "DONE"),
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "작성한 요약 조회 성공"),
            @ApiResponse(responseCode = "400", description = "작성한 요약 조회 실패")
    })
    public ApiResponseDto<List<MySummaryResponseDto>> getMySummary(
            Authentication authentication,
            @RequestParam(value = "status",defaultValue = "DONE") Status status
    ){
        return ApiResponseDto.success(SuccessCode.OK, mySummaryService.getMySummaries(authentication.getName(),status));
    }
}
