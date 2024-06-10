package com.knu.KnowcKKnowcK.dto.requestdto;

import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.Message;
import com.knu.KnowcKKnowcK.domain.MessageThread;
import com.knu.KnowcKKnowcK.enums.Position;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageThreadRequestDto {
    Long roomId;
    String content;

    public MessageThread toMessageThread(Member member, Message message, Position position){
        return MessageThread.builder()
                .member(member)
                .message(message)
                .createdTime(LocalDateTime.now())
                .content(content)
                .position(position)
                .build();
    }
}
