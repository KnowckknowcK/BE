package com.knu.KnowcKKnowcK.service.articleSummary;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.Summary;
import com.knu.KnowcKKnowcK.dto.responsedto.article.ArticleListResponseDto;
import com.knu.KnowcKKnowcK.enums.Category;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.repository.ArticleRepository;
import com.knu.KnowcKKnowcK.repository.MemberRepository;
import com.knu.KnowcKKnowcK.repository.SummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.knu.KnowcKKnowcK.exception.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class LoadArticlesServiceImpl implements LoadArticlesService{

    private final ArticleRepository articleRepository;

    private final SummaryRepository summaryRepository;

    private final MemberRepository memberRepository;

    @Override
    public Page<ArticleListResponseDto> loadArticles(Category category, int page, long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("createdTime"));
        Pageable pageable = PageRequest.of(page, 5, Sort.by(sorts));
        Page<Article> articles = articleRepository.findByCategory(category, pageable);

        Page<ArticleListResponseDto> articlesResponse = articles.map(article -> {
            Optional<Summary> summary = summaryRepository.findByArticleAndWriter(article, member);

            if (summary.isPresent()) {
                return ArticleListResponseDto.from(article, true);
            }

            return ArticleListResponseDto.from(article, false);
        });

        return articlesResponse;

    }

    @Override
    public Optional<Article> loadArticleById(Long id) {
        return articleRepository.findById(id);
    }
}
