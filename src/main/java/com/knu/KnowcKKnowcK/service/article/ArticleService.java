package com.knu.KnowcKKnowcK.service.article;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.dto.responsedto.article.ArticleResponseDto;
import com.knu.KnowcKKnowcK.enums.Category;
import com.knu.KnowcKKnowcK.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;


    @Transactional(readOnly = true)
    public List<ArticleResponseDto> getRecommendedArticles(){

        return articleRepository.find1ByCategory().stream()
                .map(ArticleResponseDto::from)
                .collect(Collectors.toList());
    }

}
