package com.knu.KnowcKKnowcK.domain;

import com.knu.KnowcKKnowcK.enums.Position;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
public class Opinion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id",nullable = false)
    private Member writer;
    @ManyToOne
    private Article article;
    @Lob
    private String content;
    @Enumerated(EnumType.STRING)
    private Position position;
    private LocalDateTime createdTime;
}
