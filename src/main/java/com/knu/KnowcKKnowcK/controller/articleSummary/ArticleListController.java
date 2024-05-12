package com.knu.KnowcKKnowcK.controller.articleSummary;

import com.knu.KnowcKKnowcK.apiResponse.ApiResponseDto;
import com.knu.KnowcKKnowcK.apiResponse.SuccessCode;
import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.dto.responsedto.article.ArticleListResponseDto;
import com.knu.KnowcKKnowcK.enums.Category;
import com.knu.KnowcKKnowcK.service.articleSummary.LoadArticlesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/article", produces = "application/json;charset=UTF-8")
@Tag(name = "지문 조회 관련 API", description = "지문 조회 관련 API")
public class ArticleListController {

    @Autowired
    LoadArticlesService loadArticlesService;

    @Operation(summary = "카테고리 별 지문 목록 조회", description = "카테고리 별로 지문 목록을 조회한다. page 번호와 Category를 PathVariable로 받는다.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "지문 목록 조회 성공"),
            @ApiResponse(responseCode = "404", description = "지문 목록 조회 실패")})
    @GetMapping("/list/{category}/{page}")
    ApiResponseDto<Page<ArticleListResponseDto>> loadArticles(@PathVariable @Valid Category category, @PathVariable @Valid int page){
        return ApiResponseDto.success(SuccessCode.OK, loadArticlesService.loadArticles(category, page, "admin"));
    }

    /** loadArticleById: frontend 구현에서는 목록에서 클릭 시 기사 정보를 api 호출 없이 페이지로 넘기도록 해서 필요없는 기능이 될 수도 있음.**/

    @GetMapping("/{articleId}")
    @Operation(summary = "지문 개별 조회", description = "문해력 진단을 할 지문 1개를 id로 조회한다.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "지문 조회 성공"),
            @ApiResponse(responseCode = "400", description = "지문 조회 실패")})
    ApiResponseDto<Article> loadArticleById(@PathVariable Long articleId){
        return ApiResponseDto.success(SuccessCode.OK, loadArticlesService.loadArticleById(articleId));
    }

}
