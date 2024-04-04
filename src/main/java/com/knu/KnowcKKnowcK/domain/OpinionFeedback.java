package com.knu.KnowcKKnowcK.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
public class OpinionFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "opinion_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne
    private Opinion opinion;
    @Lob
    private String content;
}
