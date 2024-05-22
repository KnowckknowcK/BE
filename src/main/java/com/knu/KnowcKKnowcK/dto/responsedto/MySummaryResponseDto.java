package com.knu.KnowcKKnowcK.dto.responsedto;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.domain.Summary;
import com.knu.KnowcKKnowcK.domain.SummaryFeedback;
import com.knu.KnowcKKnowcK.enums.Category;
import com.knu.KnowcKKnowcK.enums.Score;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MySummaryResponseDto {
    //작성을 완료한 요약은 피드백과 함께 반환해야한다
    private Long summaryId;
    private String title;
    @Nullable
    private Article article;
    private String content;
    private LocalDateTime createdTime;
    @Nullable
    private Score score;
    @Nullable
    private String feedBackContent;
    private Long takenTime;
    private Category category;

    public MySummaryResponseDto(Summary summary) {
        this.summaryId = summary.getId();
        this.article = summary.getArticle();
        this.title = summary.getArticle().getTitle();
        this.content = summary.getContent();
        this.createdTime = summary.getCreatedTime();
        this.category = summary.getArticle().getCategory();
    }

    public MySummaryResponseDto(Summary summary, SummaryFeedback summaryFeedback) {
        this.summaryId = summary.getId();
        this.article = summary.getArticle();
        this.content = summary.getContent();
        this.title = summary.getArticle().getTitle();
        this.createdTime = summary.getCreatedTime();
        this.score = summaryFeedback.getScore();
        this.feedBackContent = summaryFeedback.getContent();
        this.takenTime = summary.getTakenTime();
        this.category = summary.getArticle().getCategory();
    }
}
