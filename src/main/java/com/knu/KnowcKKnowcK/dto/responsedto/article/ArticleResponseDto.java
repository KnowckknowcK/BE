package com.knu.KnowcKKnowcK.dto.responsedto.article;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.enums.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class ArticleResponseDto {
    private Long id;
    private String title;
    private Category category;

    @Builder
    private ArticleResponseDto(Long id, String title, Category category){
        this.id = id;
        this.title = title;
        this.category = category;
    }

    public static ArticleResponseDto from(Article article){
        return ArticleResponseDto.builder()
                .id(article.getId())
                .title(article.getTitle())
                .category(article.getCategory())
                .build();
    }
}
