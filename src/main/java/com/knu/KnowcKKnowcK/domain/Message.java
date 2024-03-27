package com.knu.KnowcKKnowcK.domain;

import com.knu.KnowcKKnowcK.dto.MessageDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @ManyToOne
    @JoinColumn(name="debate_room_id")
    private DebateRoom debateRoomId;
    private String writer;
    private String content;
    private LocalDateTime createTime;

    public MessageDTO toMessageDTO(){
        return MessageDTO.builder()
                .content(content)
                .createTime(createTime)
                .roomId(debateRoomId.getDebateRoomId())
                .writer(writer)
                .build();
    }
}
