package com.knu.KnowcKKnowcK.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OpinionFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
  
    @Lob
    private String content;

    @JoinColumn(name = "opinion_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne
    private Opinion opinion;
}
