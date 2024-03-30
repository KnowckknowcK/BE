package com.knu.KnowcKKnowcK.domain;

import com.knu.KnowcKKnowcK.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Summary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="writer_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member writer;

    @Lob
    private String content;

    private LocalDateTime createdTime;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Long takenTime;

}
