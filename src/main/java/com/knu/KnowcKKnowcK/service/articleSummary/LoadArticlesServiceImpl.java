package com.knu.KnowcKKnowcK.service.articleSummary;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.enums.Category;
import com.knu.KnowcKKnowcK.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoadArticlesServiceImpl implements LoadArticlesService{

    private final ArticleRepository articleRepository;

    @Override
    public Page<Article> loadArticles(Category category, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("createdTime"));
        Pageable pageable = PageRequest.of(page, 5, Sort.by(sorts));
        return articleRepository.findByCategory(category, pageable);
    }

    @Override
    public Optional<Article> loadArticleById(Long id) {
        return articleRepository.findById(id);
    }
}
