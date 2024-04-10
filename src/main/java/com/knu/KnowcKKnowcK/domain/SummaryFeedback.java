package com.knu.KnowcKKnowcK.domain;

import com.knu.KnowcKKnowcK.converter.StringToScoreConverter;
import com.knu.KnowcKKnowcK.enums.Score;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SummaryFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String content;

    @Convert(converter = StringToScoreConverter.class)
    private Score score;

    @OneToOne
    @JoinColumn(name = "summary_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Summary summary;
}
