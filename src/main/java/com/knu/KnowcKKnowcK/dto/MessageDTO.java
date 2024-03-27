package com.knu.KnowcKKnowcK.dto;


import com.knu.KnowcKKnowcK.domain.DebateRoom;
import com.knu.KnowcKKnowcK.domain.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class MessageDTO {
    Long roomId;
    String writer;
    String content;
    LocalDateTime createTime;

    public Message toMessage(DebateRoom debateRoom){
        return Message.builder()
                .debateRoomId(debateRoom)
                .content(content)
                .writer(writer)
                .createTime(LocalDateTime.now())
                .build();
    }
}
