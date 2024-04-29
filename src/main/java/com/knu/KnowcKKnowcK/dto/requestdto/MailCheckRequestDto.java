package com.knu.KnowcKKnowcK.dto.requestdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MailCheckRequestDto {
    @Email
    @NotEmpty(message = "이메일을 입력하세요.")
    private String email;
}
