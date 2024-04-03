package com.knu.KnowcKKnowcK.dto.responsedto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.knu.KnowcKKnowcK.domain.OpinionFeedback;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class OpinionResponseDto {

    private Long id;

    private String content;

    private String returnMessage;

    private LocalDate localDate;


    public OpinionResponseDto(OpinionFeedback feedback){
        this.id = feedback.getId();
        this.content = feedback.getContent();
    }

    public OpinionResponseDto(String msg){
        this.returnMessage = msg;
        this.localDate = LocalDate.now();
    }
}
