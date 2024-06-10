package com.knu.KnowcKKnowcK.domain;

import com.knu.KnowcKKnowcK.enums.Score;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.util.Pair;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SummaryFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10000)
    private String content;

    @Enumerated(EnumType.STRING)
    private Score score;

    @OneToOne
    @JoinColumn(name = "summary_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Summary summary;

    public static SummaryFeedback from(Summary summary, Pair<Score, String> pairs){
        return SummaryFeedback.builder()
                .content(pairs.getSecond())
                .score(pairs.getFirst())
                .summary(summary)
                .build();
    }

}
