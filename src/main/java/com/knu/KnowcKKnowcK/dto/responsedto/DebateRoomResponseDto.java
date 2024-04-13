package com.knu.KnowcKKnowcK.dto.responsedto;

import lombok.Data;

@Data
public class DebateRoomResponseDto {
    private Double ratio;
    private Long agreeNum;
    private Long disagreeNum;

    public DebateRoomResponseDto(double ratio, long agreeNum, long disagreeNum){
        this.ratio =ratio;
        this.agreeNum = agreeNum;
        this.disagreeNum = disagreeNum;
    }
}
