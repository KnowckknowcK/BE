package com.knu.KnowcKKnowcK.controller.articleSummary;

import com.knu.KnowcKKnowcK.apiResponse.ApiResponseDto;
import com.knu.KnowcKKnowcK.apiResponse.SuccessCode;
import com.knu.KnowcKKnowcK.dto.requestdto.OpinionRequestDto;
import com.knu.KnowcKKnowcK.dto.responsedto.MyOpinionResponseDto;
import com.knu.KnowcKKnowcK.dto.responsedto.OpinionResponseDto;
import com.knu.KnowcKKnowcK.service.myPage.MyOpinionService;
import com.knu.KnowcKKnowcK.service.opinion.OpinionService;
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
@RequestMapping(value = "/api/opinion", produces = "application/json;charset=UTF-8")
@Tag(name = "견해 작성 관련 API", description = "견해 관련 API")
public class OpinionController {

    private final OpinionService opinionService;
    private final MyOpinionService myOpinionService;


    @PostMapping("/submit")
    @Operation(summary = "요약 피드백 받기", description = "작성한 요약에 대한 AI  피드백에 제공됨")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "요약 피드백 반환 성공"),
            @ApiResponse(responseCode = "400", description = "요약 피드백 반환 실패")
    })
    ApiResponseDto<OpinionResponseDto> getOpinionFeedback(Authentication authentication, @RequestBody @Valid OpinionRequestDto dto){

        OpinionResponseDto opinionFeedback = opinionService.getOpinionFeedback(dto, authentication.getName());
        return ApiResponseDto.success(SuccessCode.OK, opinionFeedback);
    }

    @GetMapping("")
    @Operation(summary="사용자 견해",description="사용자가 작성한 견해문 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "작성한 견해 조회 성공"),
            @ApiResponse(responseCode = "400", description = "작성한 견해 조회 실패")
    })
    public ApiResponseDto<List<MyOpinionResponseDto>> getMySummary(
            Authentication authentication
    ){
        return ApiResponseDto.success(SuccessCode.OK,myOpinionService.getMyOpinions(authentication.getName()));
    }

    @GetMapping("/{id}")
    @Operation(summary="사용자 견해",description="사용자가 작성한 개별 견해 조회")
    @Parameters(
            {@Parameter(name = "articleId", description = "조회하고자하는 견해에 대한 기사 아이디", example = "3")}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "작성한 개별 견해 조회 성공"),
            @ApiResponse(responseCode = "400", description = "작성한 개별 견해 조회 실패")
    })
    public ApiResponseDto<MyOpinionResponseDto> getMySummary(
            Authentication authentication,
            @PathVariable("id") Long articleId
    ){
        return ApiResponseDto.success(SuccessCode.OK,myOpinionService.getMySingleOpinion(authentication.getName(),articleId));
    }
}
