package com.knu.KnowcKKnowcK.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
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
