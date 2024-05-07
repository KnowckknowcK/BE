package com.knu.KnowcKKnowcK.controller.articleSummary;

import com.knu.KnowcKKnowcK.apiResponse.ApiResponseDto;
import com.knu.KnowcKKnowcK.apiResponse.SuccessCode;
import com.knu.KnowcKKnowcK.dto.requestdto.OpinionRequestDto;
import com.knu.KnowcKKnowcK.dto.responsedto.OpinionResponseDto;
import com.knu.KnowcKKnowcK.service.articleOpinion.SaveOpinionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/opinion", produces = "application/json;charset=UTF-8")
@Tag(name = "견해 작성 관련 API", description = "견해 관련 API")
public class OpinionController {

    @Autowired
    private SaveOpinionService saveOpinionService;

    @PostMapping("/submit")
    @Operation(summary = "요약 피드백 받기", description = "작성한 요약에 대한 AI  피드백에 제공됨")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "요약 피드백 반환 성공"),
            @ApiResponse(responseCode = "400", description = "요약 피드백 반환 실패")})
    ApiResponseDto<OpinionResponseDto> getOpinionFeedback(@RequestBody @Valid OpinionRequestDto dto){

        OpinionResponseDto opinionFeedback = saveOpinionService.getOpinionFeedback(dto);
        return ApiResponseDto.success(SuccessCode.OK, opinionFeedback);
    }

}
