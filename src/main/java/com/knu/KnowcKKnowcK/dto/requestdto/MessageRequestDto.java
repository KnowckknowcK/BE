package com.knu.KnowcKKnowcK.dto.requestdto;


import com.knu.KnowcKKnowcK.domain.DebateRoom;
import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.Message;
import com.knu.KnowcKKnowcK.enums.Position;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageRequestDto {
    Long roomId;
    String content;

    public Message toMessage(Member member, DebateRoom debateRoom, Position position){
        return Message.builder()
                .member(member)
                .debateRoom(debateRoom)
                .content(content)
                .createdTime(LocalDateTime.now())
                .position(position)
                .build();
    }
}
