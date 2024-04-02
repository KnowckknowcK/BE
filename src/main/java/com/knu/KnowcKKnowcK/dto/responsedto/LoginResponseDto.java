package com.knu.KnowcKKnowcK.dto.responsedto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDto {
    private String email;
    private String name;
    private String profileImg;
}