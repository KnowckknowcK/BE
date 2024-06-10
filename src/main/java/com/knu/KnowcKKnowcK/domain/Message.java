package com.knu.KnowcKKnowcK.domain;

import com.knu.KnowcKKnowcK.dto.responsedto.MessageResponseDto;
import com.knu.KnowcKKnowcK.enums.Position;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.List;

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

    @Column(length = 10000)
    private String content;
    private LocalDateTime createdTime;
    private Position position;

    @OneToMany(mappedBy = "message", fetch = FetchType.LAZY)
    private List<MessageThread> messageThreads;

    @OneToMany(mappedBy = "message", fetch = FetchType.LAZY)
    private List<Preference> preferences;

    public MessageResponseDto toMessageResponseDto(long likesNum, long threadNum, String profileImage){
        return MessageResponseDto.builder()
                .writer(member.getName())
                .likesNum(likesNum)
                .profileImage(profileImage)
                .content(content)
                .createdTime(formatToLocalDateTime(createdTime))
                .roomId(debateRoom.getId())
                .messageId(id)
                .position(position.name())
                .threadNum(threadNum)
                .build();
    }
}
