package com.knu.KnowcKKnowcK.service;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.dto.responsedto.article.ArticleResponseDto;
import com.knu.KnowcKKnowcK.enums.Category;
import com.knu.KnowcKKnowcK.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private static final Category[] categoryList =
            { Category.IT, Category.ECONOMICS, Category.POLITICS };

    public List<ArticleResponseDto> getRecommandedArticles(){
        List<ArticleResponseDto> articleList = new ArrayList<>();

        for (Category category: categoryList){
            Article article = articleRepository.findTop1ByCategory(category).orElse(new Article());
            articleList.add(ArticleResponseDto.from(article));
        }

        return articleList;
    }

}
