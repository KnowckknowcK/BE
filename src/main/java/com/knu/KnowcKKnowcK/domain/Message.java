package com.knu.KnowcKKnowcK.domain;

import com.knu.KnowcKKnowcK.dto.requestdto.MessageRequestDTO;
import com.knu.KnowcKKnowcK.dto.responsedto.MessageResponseDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @JoinColumn(name="debate_room_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DebateRoom debateRoomId;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member memberId;

    private String content;
    private LocalDateTime createdTime;

    public MessageResponseDTO toMessageDTO(){
        return MessageResponseDTO.builder()
                .writer(memberId.getName())
                .content(content)
                .createdTime(createdTime)
                .roomId(debateRoomId.getDebateRoomId())
                .messageId(messageId)
                .build();
    }
}
