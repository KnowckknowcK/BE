package com.knu.KnowcKKnowcK.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class DebateRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long debateRoomId;
    @OneToOne
    @JoinColumn(name="article_id")
    private Article articleId;

    private String title;
    private Double ratio;
    private Double score;
}
