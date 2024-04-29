package com.knu.KnowcKKnowcK.dto.responsedto;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.domain.Summary;
import com.knu.KnowcKKnowcK.domain.SummaryFeedback;
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
    @Nullable
    private Long feedbackId;
    private Article article;
    private String content;
    private LocalDateTime createdTime;
    @Nullable
    private Score score;
    @Nullable
    private String feedBackContent;
    //private Long takenTime; -> 마이페이지에서도 보여줄지 보류

    public MySummaryResponseDto(Summary summary){
        this.summaryId = summary.getId();
        this.article = summary.getArticle();
        this.content = summary.getContent();
        this.createdTime = summary.getCreatedTime();
    }

    public MySummaryResponseDto(Summary summary, SummaryFeedback summaryFeedback){
        this.summaryId = summary.getId();
        this.feedbackId = summaryFeedback.getId();
        this.article = summary.getArticle();
        this.content = summary.getContent();
        this.createdTime = summary.getCreatedTime();
        this.score = summaryFeedback.getScore();
        this.feedBackContent = summaryFeedback.getContent();
    }

}
