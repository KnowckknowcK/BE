package com.knu.KnowcKKnowcK.service.articleSummary;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.Summary;
import com.knu.KnowcKKnowcK.dto.responsedto.SummaryHistoryResponseDto;
import com.knu.KnowcKKnowcK.enums.Status;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import com.knu.KnowcKKnowcK.repository.ArticleRepository;
import com.knu.KnowcKKnowcK.repository.MemberRepository;
import com.knu.KnowcKKnowcK.repository.SummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoadSummaryServiceImpl implements LoadSummaryService{

    private final SummaryRepository summaryRepository;

    private final ArticleRepository articleRepository;

    private final MemberRepository memberRepository;

    @Override
    public SummaryHistoryResponseDto loadSummaryHistory(String userEmail, long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(()-> new CustomException(ErrorCode.INVALID_INPUT));
        Member member = memberRepository.findByEmail(userEmail).orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
        Optional<Summary> existedSummary = summaryRepository.findByArticleAndWriter(article, member);

        if (existedSummary.isEmpty() || (existedSummary.isPresent() && existedSummary.get().getStatus() == Status.DONE)) {
            return new SummaryHistoryResponseDto(Status.NEW);
        }

        return new SummaryHistoryResponseDto(existedSummary.get());
    }
}
