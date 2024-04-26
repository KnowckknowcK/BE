package com.knu.KnowcKKnowcK.service.articleSummary;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.enums.Category;
import com.knu.KnowcKKnowcK.repository.ArticleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;



@ExtendWith(MockitoExtension.class)
class LoadArticlesServiceTest {

    @InjectMocks
    private LoadArticlesServiceImpl sut;

    @Mock
    private ArticleRepository articleRepository;

    @Test
    @DisplayName("지문 목록 조회에 성공한다.")
    void loadArticles() {
        int pageNum = 0;
        Page<Article> page = createPage();
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("createdTime"));
        Pageable pageable = PageRequest.of(pageNum, 5, Sort.by(sorts));

        Mockito.when(articleRepository.findByCategory(Category.CULTURE, pageable))
                .thenReturn(page);

        Page<Article> loaded = sut.loadArticles(Category.CULTURE, pageNum);

        Assertions.assertThat(loaded.getTotalElements()).isEqualTo(7);

    }

    Article createArticle(String title){
        Article article = new Article();
        article.setTitle(title);
        article.setContent("Content");

        return article;
    }

    Page<Article> createPage(){
        List<Article> articles = new ArrayList<>();
        articles.add(createArticle("title1"));
        articles.add(createArticle("title2"));
        articles.add(createArticle("title3"));
        articles.add(createArticle("title4"));
        articles.add(createArticle("title5"));
        articles.add(createArticle("title6"));
        articles.add(createArticle("title7"));

        Page<Article> page = new PageImpl<>(articles);

        return page;
    }
}