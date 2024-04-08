package com.knu.KnowcKKnowcK.service.articleSummary;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.enums.Category;

import java.util.List;
import java.util.Optional;

public interface LoadArticlesService {

    List<Article>  loadArticles(Category category);

    Optional<Article> loadArticleById(Long id);

}
