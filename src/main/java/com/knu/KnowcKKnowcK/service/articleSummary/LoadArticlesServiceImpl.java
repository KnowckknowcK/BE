package com.knu.KnowcKKnowcK.service.articleSummary;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoadArticlesServiceImpl implements LoadArticlesService{

    @Autowired
    private final ArticleRepository articleRepository;

    @Override
    public List<Article> loadArticles() {
        return articleRepository.findAll();
    }

    @Override
    public Optional<Article> loadArticleById(Long id) {
        return articleRepository.findById(id);
    }
}
