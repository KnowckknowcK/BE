package com.knu.KnowcKKnowcK.dto.responsedto;

import com.knu.KnowcKKnowcK.domain.*;
import com.knu.KnowcKKnowcK.enums.Score;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MyOpinionResponseDTO {
    //작성한 요약은 피드백과 함께 반환해야한다
    private Long opinionId;
    private Long feedbackId;
    private Article article;
    private String content;
    private String feedBackContent;

    public MyOpinionResponseDTO(Opinion opinion, OpinionFeedback opinionFeedback){
        this.opinionId = opinion.getId();
        this.feedbackId = opinionFeedback.getId();
        this.article = opinion.getArticle();
        this.content = opinion.getContent();
        this.feedBackContent = opinionFeedback.getContent();
    }
}
