package com.knu.KnowcKKnowcK.dto.requestdto;


import com.knu.KnowcKKnowcK.domain.DebateRoom;
import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.Message;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageRequestDTO {
    Long roomId;
    String content;

    public Message toMessage( Member member, DebateRoom debateRoom){
        return Message.builder()
                .memberId(member)
                .debateRoomId(debateRoom)
                .content(content)
                .createdTime(LocalDateTime.now())
                .build();
    }
}
