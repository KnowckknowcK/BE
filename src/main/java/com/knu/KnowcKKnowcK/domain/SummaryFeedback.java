package com.knu.KnowcKKnowcK.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SummaryFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String content;

    private int score;

    @OneToOne
    @JoinColumn(name = "summary_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Summary summary;
}
