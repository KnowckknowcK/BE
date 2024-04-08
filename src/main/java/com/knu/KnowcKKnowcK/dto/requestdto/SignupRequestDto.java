package com.knu.KnowcKKnowcK.dto.requestdto;

import lombok.Data;

@Data
public class SignupRequestDto {
    private String email;
    private String name;
    private String password;
    private String profileImg;
}
