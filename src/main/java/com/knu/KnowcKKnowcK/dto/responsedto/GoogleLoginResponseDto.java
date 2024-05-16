package com.knu.KnowcKKnowcK.dto.responsedto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GoogleLoginResponseDto {
    private String jwt;
}
