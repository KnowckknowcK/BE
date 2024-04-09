package com.knu.KnowcKKnowcK.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class MessageThreadResponseDto {
    Long id;
    Long parentMessageId;
    String writer;
    String profileImage;
    String position;
    String content;
    LocalDateTime createdTime;
}
