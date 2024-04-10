package com.knu.KnowcKKnowcK.dto.responsedto;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.domain.Summary;
import com.knu.KnowcKKnowcK.domain.SummaryFeedback;
import com.knu.KnowcKKnowcK.enums.Score;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MyDoneSummaryResponseDto {
    //작성한 요약은 피드백과 함께 반환해야한다
    private Long summaryId;
    private Long feedbackId;
    private Article article;
    private String content;
    private LocalDateTime createdTime;
    private Score score;
    private String feedBackContent;
    //private Long takenTime; -> 마이페이지에서도 보여줄지 보류

    public MyDoneSummaryResponseDto(Summary summary, SummaryFeedback summaryFeedback){
        this.summaryId = summary.getId();
        this.feedbackId = summaryFeedback.getId();
        this.article = summary.getArticle();
        this.content = summary.getContent();
        this.createdTime = summary.getCreatedTime();
        this.score = summaryFeedback.getScore();
        this.feedBackContent = summaryFeedback.getContent();
    }

}
