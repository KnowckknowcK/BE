package com.knu.KnowcKKnowcK.dto.responsedto;

import com.knu.KnowcKKnowcK.domain.*;
import com.knu.KnowcKKnowcK.enums.Category;
import com.knu.KnowcKKnowcK.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MyOpinionResponseDto {
    //작성한 요약은 피드백과 함께 반환해야한다
    private Long opinionId;
    private Article article;
    private String content;
    private String feedBackContent;
    private Position position;
    private LocalDateTime createdTime;
    private Category category;

    public MyOpinionResponseDto(Opinion opinion){
        this.opinionId = opinion.getId();
        this.article = opinion.getArticle();
        this.content = opinion.getContent();
        this.feedBackContent = opinion.getFeedbackContent();
        this.position = opinion.getPosition();
        this.createdTime = opinion.getCreatedTime();
        this.category = opinion.getArticle().getCategory();
    }
}
