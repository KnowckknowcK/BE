package com.knu.KnowcKKnowcK.controller.articleSummary;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.enums.Category;
import com.knu.KnowcKKnowcK.service.articleSummary.LoadArticlesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class LoadArticlesController {

    @Autowired
    LoadArticlesService loadArticlesService;

    @Operation(summary = "지문 목록 조회", description = "문해력 진단을 할 지문 목록을 조회한다.")
    @Tag(name = "지문 목록 조회", description = "모든 지문을 목록 형식으로 조회할 수 있는 API")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "지문 목록 조회 성공"),
                    @ApiResponse(responseCode = "400", description = "지문 목록 조회 실패")})
    @GetMapping("/api/article/list/{category}/{page}")
    ResponseEntity<Page<Article>> loadArticles(@PathVariable @Valid Category category, @PathVariable @Valid int page){
        return ResponseEntity.ok(loadArticlesService.loadArticles(category, page));
    }

    @Operation(summary = "지문 개별 조회", description = "문해력 진단을 할 지문 1개를 id로 조회한다.")
    @Tag(name = "지문 개별 조회", description = "지문 id를 이용하여 지문 내용을 조회할 수 있는 API")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "지문 조회 성공"),
            @ApiResponse(responseCode = "400", description = "지문 조회 실패")})
    @GetMapping("/api/article/{articleId}")
    ResponseEntity<Optional<Article>> loadArticleById(@PathVariable Long articleId){
        return ResponseEntity.ok((loadArticlesService.loadArticleById(articleId)));
    }

}
