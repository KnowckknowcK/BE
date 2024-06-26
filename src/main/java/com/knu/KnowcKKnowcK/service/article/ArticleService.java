package com.knu.KnowcKKnowcK.service.article;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.Opinion;
import com.knu.KnowcKKnowcK.domain.Summary;
import com.knu.KnowcKKnowcK.dto.responsedto.article.ArticleListResponseDto;
import com.knu.KnowcKKnowcK.dto.responsedto.article.ArticleResponseDto;
import com.knu.KnowcKKnowcK.enums.Category;
import com.knu.KnowcKKnowcK.enums.Status;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import com.knu.KnowcKKnowcK.repository.ArticleRepository;
import com.knu.KnowcKKnowcK.repository.MemberRepository;
import com.knu.KnowcKKnowcK.repository.OpinionRepository;
import com.knu.KnowcKKnowcK.repository.SummaryRepository;
import com.knu.KnowcKKnowcK.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.knu.KnowcKKnowcK.exception.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final RedisUtil redisUtil;
    private final SummaryRepository summaryRepository;
    private final MemberRepository memberRepository;
    private final OpinionRepository opinionRepository;


    @Transactional(readOnly = true)
    public List<ArticleResponseDto> getRecommendedArticles(){
        String key = LocalDate.now().toString();
        List<ArticleResponseDto> responseDtoList = redisUtil.getDataList(key, ArticleResponseDto.class);

        if (responseDtoList.isEmpty()) {
            responseDtoList = articleRepository.find1ByCategory().stream()
                    .map(ArticleResponseDto::from)
                    .collect(Collectors.toList());
            redisUtil.setDataList(key, responseDtoList);
        }

        return responseDtoList;
    }

    @Transactional(readOnly = true)
    public Page<ArticleListResponseDto> loadArticles(Category category, int page, String memberEmail) {

        Member member = memberRepository.findByEmail(memberEmail).orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("createdTime"));
        Pageable pageable = PageRequest.of(page, 5, Sort.by(sorts));
        Page<Article> articles = articleRepository.findByCategory(category, pageable);

        Page<ArticleListResponseDto> articlesResponse = articles.map(article -> {
            Optional<Summary> summary = summaryRepository.findByArticleAndWriter(article, member);
            Optional<Opinion> opinion = opinionRepository.findByArticleAndWriter(article, member);

            return ArticleListResponseDto.from(article,
                    summary.isPresent() && summary.get().getStatus().equals(Status.DONE),
                    opinion.isPresent()
            );
        });
        return articlesResponse;
    }

    public Article loadArticleById(Long id) {
        return articleRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
    }

}
