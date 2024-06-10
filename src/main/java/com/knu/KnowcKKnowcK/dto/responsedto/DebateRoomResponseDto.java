package com.knu.KnowcKKnowcK.dto.responsedto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DebateRoomResponseDto {
    private Long agreeNum;
    private Long agreeLikesNum;
    private Long disagreeNum;
    private Long disagreeLikesNum;
    private String title;
    private String position;
    private LocalDateTime now;

    public DebateRoomResponseDto(
            long agreeNum,
            long disagreeNum,
            long agreeLikesNum,
            long disagreeLikesNum,
            String title,
            String position

    ){
        this.agreeNum = agreeNum;
        this.agreeLikesNum = agreeLikesNum;
        this.disagreeNum = disagreeNum;
        this.disagreeLikesNum = disagreeLikesNum;
        this.title = title;
        this.position = position;
        this.now = LocalDateTime.now();
    }
}
