package com.knu.KnowcKKnowcK.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;
    private String title;
    @Lob
    private String content;
    private LocalDateTime createdTime;
    private String articleUrl;
}
