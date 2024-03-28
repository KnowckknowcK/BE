package com.knu.KnowcKKnowcK.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class MemberDebate {
    @Id
    private Long memberDebateId;
    @ManyToOne
    @JoinColumn(name="member_id")
    private Member memberId;
    @ManyToOne
    @JoinColumn(name="debate_room_id")
    private DebateRoom debateRoomId;

    private String side;
}
