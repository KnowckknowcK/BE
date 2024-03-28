package com.knu.KnowcKKnowcK.domain;

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
public class MessageThread {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageThreadId;
    @ManyToOne
    @JoinColumn(name="message_id")
    private Message messageId;
    @ManyToOne
    @JoinColumn(name="member_id")
    private Member memberId;

    private String content;
    private LocalDateTime createdTime;
}
