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
    @ManyToOne
    @JoinColumn(name="member_id")
    private Member memberId;

    private String content;
    private LocalDateTime createdTime;

    public MessageDTO toMessageDTO(){
        return MessageDTO.builder()
                .content(content)
                .createdTime(createdTime)
                .roomId(debateRoomId.getDebateRoomId())
                .build();
    }
}
