package com.knu.KnowcKKnowcK.domain;

import com.knu.KnowcKKnowcK.dto.responsedto.MessageThreadResponseDto;
import com.knu.KnowcKKnowcK.enums.Position;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

import static com.knu.KnowcKKnowcK.service.debateRoom.DebateRoomUtil.formatToLocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class MessageThread {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="message_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Message message;
    @ManyToOne
    @JoinColumn(name="member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    private String content;
    private LocalDateTime createdTime;
    private Position position;

    public MessageThreadResponseDto toMessageThreadResponseDto(Long parentMessageId, String profileImage){
        return MessageThreadResponseDto.builder()
                .id(id)
                .content(content)
                .position(position.name())
                .profileImage(profileImage)
                .createdTime(formatToLocalDateTime(createdTime))
                .parentMessageId(parentMessageId)
                .writer(member.getName())
                .build();
    }
}
