package com.knu.KnowcKKnowcK.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class MemberDebateId implements Serializable {
    @Column(name = "member_id")
    private Long memberId ;

    @Column(name = "debate_room_id")
    private Long debateRoomId;

    public MemberDebateId(Long memberId, Long debateRoomId) {
        this.memberId = memberId;
        this.debateRoomId = debateRoomId;
    }
}
