package com.knu.KnowcKKnowcK.domain;

import com.knu.KnowcKKnowcK.enums.Category;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String title;

    @Lob
    private String content;

    private LocalDateTime createdTime;

    private String articleUrl;
}
