package com.knu.KnowcKKnowcK.controller.articleSummary;

import com.knu.KnowcKKnowcK.dto.responsedto.SummaryHistoryResponseDto;
import com.knu.KnowcKKnowcK.service.articleSummary.LoadSummaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/summary", produces = "application/json;charset=UTF-8")
public class LoadSummaryController {

    @Autowired
    private final LoadSummaryService loadSummaryService;

    @Operation(summary = "요약 진행 내역 가져오기", description = "요약 작성 시작 시 히스토리가 있는 경우 이전 소요 시간과 요약 내용 로딩")
    @Tag(name = "요약 진행 내역 가져오기", description = "요약 작성 시작 시 히스토리가 있는 경우 Status는 ING이고, 이전 소요 시간과 요약 내용 로딩됨. 히스토리가 없는 경우는 Status NEW만 반환")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "요약 히스토리 가져오기 성공"),
            @ApiResponse(responseCode = "400", description = "요약 히스토리 가져오기 실패")})
    @GetMapping("/load/")
    ResponseEntity<SummaryHistoryResponseDto> loadSummaryHistory(@RequestParam @Valid long userId, @RequestParam @Valid int articleId){

        SummaryHistoryResponseDto responseDto = loadSummaryService.loadSummaryHistory(userId, articleId);

        return ResponseEntity.ok(responseDto);
    }
}
