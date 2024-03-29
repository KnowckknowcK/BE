package com.knu.KnowcKKnowcK.service.articleSummary;

import com.knu.KnowcKKnowcK.domain.Article;

import java.util.List;
import java.util.Optional;

public interface LoadArticlesService {

    List<Article>  loadArticles();

    Optional<Article> loadArticleById(Long id);

}
