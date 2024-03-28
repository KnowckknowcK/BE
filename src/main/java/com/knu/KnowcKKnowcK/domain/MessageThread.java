package com.knu.KnowcKKnowcK.domain;

import com.knu.KnowcKKnowcK.dto.responsedto.MessageThreadResponseDTO;
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
public class MessageThread {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageThreadId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="message_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Message messageId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member memberId;

    private String content;
    private LocalDateTime createdTime;

    public MessageThreadResponseDTO toMessageThreadDTO(Long parentMessageId){
        return MessageThreadResponseDTO.builder()
                .content(content)
                .createdTime(createdTime)
                .parentMessageId(parentMessageId)
                .writer(memberId.getName())
                .build();
    }
}
