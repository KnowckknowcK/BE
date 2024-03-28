package com.knu.KnowcKKnowcK.dto.requestdto;


import com.knu.KnowcKKnowcK.domain.DebateRoom;
import com.knu.KnowcKKnowcK.domain.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageRequestDTO {
    Long roomId;
    String content;

    public Message toMessage(DebateRoom debateRoom){
        return Message.builder()
                .debateRoomId(debateRoom)
                .content(content)
                .createdTime(LocalDateTime.now())
                .build();
    }
}
