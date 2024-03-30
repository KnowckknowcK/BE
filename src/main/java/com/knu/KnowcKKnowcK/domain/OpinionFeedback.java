package com.knu.KnowcKKnowcK.domain;

import jakarta.persistence.*;

public class OpinionFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(referencedColumnName = "opinion_id")
    @OneToOne
    private Opinion opinion;
    @Lob
    private String content;
}
