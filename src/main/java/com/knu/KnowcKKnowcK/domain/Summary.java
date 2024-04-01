package com.knu.KnowcKKnowcK.domain;

import com.knu.KnowcKKnowcK.enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class Summary {
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
    private LocalDateTime createdTime;
    @Enumerated(EnumType.STRING)
    private Status status;
    private Integer takenTime; //데이터타입 초를 단위로 카운팅
}
