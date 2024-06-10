package com.knu.KnowcKKnowcK.dto.responsedto;

import com.knu.KnowcKKnowcK.domain.Opinion;
import com.knu.KnowcKKnowcK.enums.Category;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OpinionResponseDto {

    private final String content;
    private final LocalDateTime createdTime;
    private final Category category;
    private final String feedbackContent;


    public OpinionResponseDto(Opinion opinion){
        this.content = opinion.getContent();
        this.createdTime = opinion.getCreatedTime();
        this.category = opinion.getArticle().getCategory();
        this.feedbackContent = opinion.getFeedbackContent();
    }
}
