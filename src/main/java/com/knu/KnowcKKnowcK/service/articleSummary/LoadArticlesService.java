package com.knu.KnowcKKnowcK.service.articleSummary;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.enums.Category;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface LoadArticlesService {

    Page<Article> loadArticles(Category category, int page);

    Optional<Article> loadArticleById(Long id);

}
