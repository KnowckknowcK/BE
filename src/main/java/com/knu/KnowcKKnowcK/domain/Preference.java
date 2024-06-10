package com.knu.KnowcKKnowcK.domain;

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
public class Preference {
    @EmbeddedId
    private PreferenceId id;

    @ManyToOne
    @MapsId("memberId")
    @JoinColumn(name="member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @ManyToOne
    @MapsId("messageId")
    @JoinColumn(name="message_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Message message;

    private boolean isAgree;
}
