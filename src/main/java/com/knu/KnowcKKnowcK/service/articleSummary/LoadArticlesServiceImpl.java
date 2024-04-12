package com.knu.KnowcKKnowcK.service.articleSummary;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.enums.Category;
import com.knu.KnowcKKnowcK.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoadArticlesServiceImpl implements LoadArticlesService{

    private final ArticleRepository articleRepository;

    @Override
    public List<Article> loadArticles(Category category) {
        return articleRepository.findByCategory(category);
    }

    @Override
    public Optional<Article> loadArticleById(Long id) {
        return articleRepository.findById(id);
    }
}
