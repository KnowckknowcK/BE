package com.knu.KnowcKKnowcK.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.knu.KnowcKKnowcK.dto.responsedto.MessageResponseDto;
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

    public MessageResponseDto toMessageResponseDto(String position, long likesNum, long threadNum, String profileImage){
        return MessageResponseDto.builder()
                .writer(member.getName())
                .likesNum(likesNum)
                .profileImage(profileImage)
                .content(content)
                .createdTime(formatToLocalDateTime(createdTime))
                .roomId(debateRoom.getId())
                .messageId(id)
                .position(position)
                .threadNum(threadNum)
                .build();
    }
}
