package com.knu.KnowcKKnowcK.controller.articleSummary;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.service.articleSummary.LoadArticlesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LoadArticlesController {

    @Autowired
    LoadArticlesService loadArticlesService;

    @Operation(summary = "지문 목록 조회", description = "문해력 진단을 할 지문 목록을 조회한다.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "지문 목록 조회 성공"),
                    @ApiResponse(responseCode = "400", description = "지문 목록 조회 실패")})
    @GetMapping("/api/article/list")
    ResponseEntity<List<Article>> loadArticles(){

        return ResponseEntity.ok(loadArticlesService.loadArticles());
    }
}
