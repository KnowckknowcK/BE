package com.knu.KnowcKKnowcK.controller.myInfo;

import com.knu.KnowcKKnowcK.apiResponse.ApiResponseDto;
import com.knu.KnowcKKnowcK.apiResponse.SuccessCode;
import com.knu.KnowcKKnowcK.enums.Status;
import com.knu.KnowcKKnowcK.service.myPage.MyHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
@Tag(name = "MyHistory",description = "사용자가 작성한 요약과 견해 조회에 관한 처리")
@RequiredArgsConstructor
@Slf4j
public class MyHistoryController {
    //TODO 현재 작성 중인 혹은 작성한 요약문 확인
    //TODO 작성한 견해문 확인

    private final MyHistoryService myHistoryService;

    @Operation(summary="사용자 요약",description="사용자가 작성했거나 작성한 요약 조회")
    @Parameters({
            @Parameter(name = "status",description = "원하는 요약이 작성 중인지,작성 한건지",example = "DONE"),
            @Parameter(name = "id", description = "임시 테스트용 추후 수정 예정", example = "1")
    })
    @GetMapping("/summary")
    public ApiResponseDto<List<?>> getMySummary(
            @RequestHeader("Authorization") Long id,
            @RequestParam(value = "status",defaultValue = "DONE") Status status
    ){
        log.info("status = {}",status);
        return ApiResponseDto.success(SuccessCode.OK,myHistoryService.getMySummaries(id,status));
    }

}
