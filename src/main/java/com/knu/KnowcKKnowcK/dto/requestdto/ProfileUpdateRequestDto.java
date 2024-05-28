package com.knu.KnowcKKnowcK.dto.requestdto;

import jakarta.annotation.Nullable;
import lombok.Getter;

@Getter
public class ProfileUpdateRequestDto {
    @Nullable
    private String name;
    @Nullable
    private String password;
}
