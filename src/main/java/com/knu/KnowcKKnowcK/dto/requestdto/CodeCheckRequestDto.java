package com.knu.KnowcKKnowcK.dto.requestdto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class CodeCheckRequestDto {
    @Email
    private String email;
    private String code;
}
