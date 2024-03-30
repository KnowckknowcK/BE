package com.knu.KnowcKKnowcK.domain;

import com.knu.KnowcKKnowcK.enums.Category;
import com.knu.KnowcKKnowcK.enums.Position;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class MemberDebate {
    @EmbeddedId
    private MemberDebateId id;
    @ManyToOne
    @MapsId("memberId")
    @JoinColumn(name="member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @ManyToOne
    @MapsId("debateRoomId")
    @JoinColumn(name="debate_room_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DebateRoom debateRoom;

    @Enumerated(EnumType.STRING)
    private Position position;
}
