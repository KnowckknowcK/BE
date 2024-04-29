package com.knu.KnowcKKnowcK.dto.responsedto;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.domain.Summary;
import lombok.AllArgsConstructor;

import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyIngSummaryResponseDto {
    private Long summaryId;
    private Article article;
    private String content;
    //Nullable로 피드백은 제외하고 한꺼번에 합쳐서 전달

    public MyIngSummaryResponseDto(Summary summary){
        this.summaryId = summary.getId();
        this.article = summary.getArticle();
        this.content = summary.getContent();
    }
}
