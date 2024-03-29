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
public class PreferenceId implements Serializable {
    @Column(name = "member_id")
    private Long memberId ;

    @Column(name = "message_id")
    private Long messageId;

    public PreferenceId(Long memberId, Long messageId) {
        this.memberId = memberId;
        this.messageId = messageId;
    }
}
