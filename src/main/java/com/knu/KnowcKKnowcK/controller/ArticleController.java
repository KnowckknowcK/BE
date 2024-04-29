package com.knu.KnowcKKnowcK.controller;

import com.knu.KnowcKKnowcK.apiResponse.ApiResponseDto;
import com.knu.KnowcKKnowcK.apiResponse.SuccessCode;
import com.knu.KnowcKKnowcK.dto.responsedto.article.ArticleResponseDto;
import com.knu.KnowcKKnowcK.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/article")
@Tag(name="Article",description="뉴스 기사 조회와 관련된 API Controller")
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/recommand")
    @Operation(summary = "추천 기사 조회 API", description = "사용자에 따른 추천기사를 3개 제공하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "조회 실패")
    })
    public ApiResponseDto<List<ArticleResponseDto>> getRecommandArticles(){
        return ApiResponseDto.success(SuccessCode.OK, articleService.getRecommandedArticles());
    }
}
