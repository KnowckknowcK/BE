package com.knu.KnowcKKnowcK.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class DebateRoomResponseDto {
    private Double ratio;
    private Long agreeNum;
    private Long disagreeNum;
}
