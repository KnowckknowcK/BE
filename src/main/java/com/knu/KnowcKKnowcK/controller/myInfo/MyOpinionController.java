package com.knu.KnowcKKnowcK.controller.myInfo;

import com.knu.KnowcKKnowcK.apiResponse.ApiResponseDto;
import com.knu.KnowcKKnowcK.apiResponse.SuccessCode;
import com.knu.KnowcKKnowcK.dto.responsedto.MyOpinionResponseDto;
import com.knu.KnowcKKnowcK.service.myPage.MyOpinionService;
import com.knu.KnowcKKnowcK.service.myPage.MySummaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/opinion")
@Tag(name = "MyOpinion",description = "내가 작성한 요약 조회에 관한 처리")
@RequiredArgsConstructor
public class MyOpinionController {
    private final MyOpinionService myOpinionService;
    @Operation(summary="사용자 견해",description="사용자가 작성한 견해문 조회")
    @Parameters({
            @Parameter(name = "id", description = "임시 테스트용 추후 수정 예정", example = "1")
    })
    @GetMapping("")
    public ApiResponseDto<List<MyOpinionResponseDto>> getMySummary(
            @RequestHeader("Authorization") Long id
    ){
        return ApiResponseDto.success(SuccessCode.OK,myOpinionService.getMyOpinions(id));
    }
}