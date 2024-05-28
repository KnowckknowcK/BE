package com.knu.KnowcKKnowcK.controller.articleSummary;

import com.knu.KnowcKKnowcK.apiResponse.ApiResponseDto;
import com.knu.KnowcKKnowcK.apiResponse.SuccessCode;
import com.knu.KnowcKKnowcK.dto.requestdto.OpinionRequestDto;
import com.knu.KnowcKKnowcK.dto.responsedto.OpinionResponseDto;
import com.knu.KnowcKKnowcK.service.opinion.OpinionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/opinion", produces = "application/json;charset=UTF-8")
@Tag(name = "견해 작성 관련 API", description = "견해 관련 API")
public class OpinionController {

    private final OpinionService opinionService;

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

}
