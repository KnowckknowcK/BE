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
public class Preference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long preferenceId;
    @ManyToOne
    @JoinColumn(name="member_id")
    private Member memberId;
    @ManyToOne
    @JoinColumn(name="message_id")
    private Message messageId;

    private boolean isLike;
}
