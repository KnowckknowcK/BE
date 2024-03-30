package com.knu.KnowcKKnowcK.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
public class MessageResponseDto {
    Long roomId;
    Long messageId;
    String writer;
    String position;
    String content;
    LocalDateTime createdTime;
}
