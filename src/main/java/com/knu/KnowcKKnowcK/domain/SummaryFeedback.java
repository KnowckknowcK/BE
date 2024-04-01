package com.knu.KnowcKKnowcK.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class SummaryFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "summary_id")
    private Summary summary;
    @Lob
    private String content;
    private Integer Score;
}
