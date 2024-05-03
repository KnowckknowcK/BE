package com.knu.KnowcKKnowcK.service.articleSummary;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.dto.responsedto.article.ArticleListResponseDto;
import com.knu.KnowcKKnowcK.enums.Category;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface LoadArticlesService {

    Page<ArticleListResponseDto> loadArticles(Category category, int page, long memberId);

    Optional<Article> loadArticleById(Long id);

}
