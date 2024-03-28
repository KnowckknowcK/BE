package com.knu.KnowcKKnowcK.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class MemberDebate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberDebateId;
    @ManyToOne
    @JoinColumn(name="member_id")
    private Member memberId;
    @ManyToOne
    @JoinColumn(name="debate_room_id")
    private DebateRoom debateRoomId;

    private String position;
}
