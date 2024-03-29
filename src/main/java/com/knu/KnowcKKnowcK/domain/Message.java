package com.knu.KnowcKKnowcK.domain;

import com.knu.KnowcKKnowcK.dto.responsedto.MessageResponseDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name="debate_room_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DebateRoom debateRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    private String content;
    private LocalDateTime createdTime;

    public MessageResponseDTO toMessageDTO(){
        return MessageResponseDTO.builder()
                .writer(member.getName())
                .content(content)
                .createdTime(createdTime)
                .roomId(debateRoom.getId())
                .messageId(id)
                .build();
    }
}
