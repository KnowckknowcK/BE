package com.knu.KnowcKKnowcK.dto.responsedto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PreferenceResponseDto {
    private Long agreeLikesNum;
    private Long disagreeLikesNum;
    private Boolean isIncrease;
}
