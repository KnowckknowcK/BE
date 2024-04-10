package com.knu.KnowcKKnowcK.dto.responsedto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.knu.KnowcKKnowcK.domain.SummaryFeedback;
import com.knu.KnowcKKnowcK.enums.Score;
import lombok.*;

import java.time.LocalDate;

@Getter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class SummaryResponseDto {

    private Long id;

    private String content;

    private Score score;

    private String returnMessage;

    private LocalDate localDate;


    public SummaryResponseDto(SummaryFeedback feedback){
        this.id = feedback.getId();
        this.content = feedback.getContent();
        this.score = feedback.getScore();
    }

    public SummaryResponseDto(String msg){
        this.returnMessage = msg;
        this.localDate = LocalDate.now();
    }
}
