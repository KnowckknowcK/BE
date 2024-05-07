package com.knu.KnowcKKnowcK.dto.responsedto;

import com.knu.KnowcKKnowcK.domain.Opinion;
import lombok.Getter;

@Getter
public class OpinionResponseDto {

    private String content;

    private String feedbackContent;


    public OpinionResponseDto(Opinion opinion){
        this.content = opinion.getContent();
        this.feedbackContent = opinion.getFeedbackContent();
    }
}
