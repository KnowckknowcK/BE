package com.knu.KnowcKKnowcK.controller.myInfo;

import com.knu.KnowcKKnowcK.apiResponse.ApiResponseDto;
import com.knu.KnowcKKnowcK.apiResponse.SuccessCode;
import com.knu.KnowcKKnowcK.dto.responsedto.MySummaryResponseDto;
import com.knu.KnowcKKnowcK.enums.Status;
import com.knu.KnowcKKnowcK.service.myPage.MySummaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/summary")
@Tag(name = "MySummary",description = "사용자가 작성한 요약에 관한 처리")
@RequiredArgsConstructor
@Slf4j
public class MySummaryController {

    private final MySummaryService mySummaryService;

    @Operation(summary="사용자 요약",description="사용자가 작성했거나 작성한 요약 조회")
    @Parameters({
            @Parameter(name = "status",description = "원하는 요약이 작성 중인지,작성 한건지",example = "DONE"),
    })
    @GetMapping("")
    public ApiResponseDto<List<MySummaryResponseDto>> getMySummary(
            Authentication authentication,
            @RequestParam(value = "status",defaultValue = "DONE") Status status
    ){
        return ApiResponseDto.success(SuccessCode.OK, mySummaryService.getMySummaries(authentication.getName(),status));
    }
}
