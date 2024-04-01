package com.knu.KnowcKKnowcK.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
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
