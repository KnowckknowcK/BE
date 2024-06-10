package com.knu.KnowcKKnowcK.dto.responsedto.article;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.enums.Category;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
public class ArticleResponseDto {
    private Long id;

    private String title;

    private Category category;

    @Column(length = 50000)
    private String content;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

    private String articleUrl;

    @Builder
    private ArticleResponseDto(Long id, String title, Category category,String content, LocalDateTime createdTime, String articleUrl){
        this.id = id;
        this.title = title;
        this.category = category;
        this.content = content;
        this.articleUrl = articleUrl;
        this.createdTime = createdTime;
    }

    public static ArticleResponseDto from(Article article){
        return ArticleResponseDto.builder()
                .id(article.getId())
                .title(article.getTitle())
                .category(article.getCategory())
                .content(article.getContent())
                .createdTime(article.getCreatedTime())
                .articleUrl(article.getArticleUrl())
                .build();
    }
}
