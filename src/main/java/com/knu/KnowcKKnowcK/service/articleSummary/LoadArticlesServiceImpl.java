package com.knu.KnowcKKnowcK.service.articleSummary;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoadArticlesServiceImpl implements LoadArticlesService{

    @Autowired
    private final ArticleRepository articleRepository;

    @Override
    public List<Article> loadArticles() {
        return articleRepository.findAll();
    }
}
