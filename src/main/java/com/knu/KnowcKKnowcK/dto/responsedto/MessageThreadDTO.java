package com.knu.KnowcKKnowcK.dto.responsedto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageThreadDTO {
    Long parentMessageId;
    String writer;
    String content;
    LocalDateTime createdTime;
}
