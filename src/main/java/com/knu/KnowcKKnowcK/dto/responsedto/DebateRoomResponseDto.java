package com.knu.KnowcKKnowcK.dto.responsedto;

import lombok.Data;

@Data
public class DebateRoomResponseDto {
    private Long agreeNum;
    private Long agreeLikesNum;
    private Long disagreeNum;
    private Long disagreeLikesNum;

    public DebateRoomResponseDto(
            long agreeNum,
            long disagreeNum,
            long agreeLikesNum,
            long disagreeLikesNum
    ){
        this.agreeNum = agreeNum;
        this.agreeLikesNum = agreeLikesNum;
        this.disagreeNum = disagreeNum;
        this.disagreeLikesNum = disagreeLikesNum;
    }
}
